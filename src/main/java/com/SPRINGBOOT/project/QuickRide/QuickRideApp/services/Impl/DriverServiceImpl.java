package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.Impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RiderDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.User;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideRequestStatus;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideStatus;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.exceptions.ResourceNotFoundException;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.DriverRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//if two request comes at the same time
// we are making all the request in (atomic decisions) transactional way (IF two req comes same time
// we are solving the first req first then the other
// but problem is we are not solving the concurrency issue
// solved by rabit mq(msg queue) in real world
// one driver cannot see the request of other

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("RideRequest cannot be accepted, status is "+ rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();
        if(!currentDriver.getAvailable()) {
            throw new RuntimeException("Driver cannot accept ride due to unavailability");
        }
        //now the driver cannot accept another ride
        currentDriver.setAvailable(false);
        Driver savedDriver = driverRepository.save(currentDriver);

        Ride ride = rideService.createNewRide(rideRequest, savedDriver);
        return modelMapper.map(ride, RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);

        //checking the driver is owing the ride or not (checking for current rider)
        Driver driver = getCurrentDriver();
        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot cancel a ride as he has not accepted it earlier");
        }

        // only ride is cancel only if it is confirmed
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: "+ride.getRideStatus());
        }

        //changing the status and update the availability
        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(driver, true);

        return modelMapper.map(ride, RideDto.class);
    }

    //
    @Override
    public RideDto startRide(Long rideId, String otp) {
        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        //
        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        // he only can start the ride if it is confirmed
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride status is not CONFIRMED hence cannot be started, status: "+ride.getRideStatus());
        }

        //if correct otp is given only the ride can start
        if(!otp.equals(ride.getOtp())) {
            throw new RuntimeException("Otp is not valid, otp: "+otp);
        }

        //generating the ride
        //and start the ride
        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot start a ride as he has not accepted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride status is not ONGOING hence cannot be ended, status: "+ride.getRideStatus());
        }

        ride.setEndedAt(LocalDateTime.now());

        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver, true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    //driver rate the rider
    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver is not the owner of this Ride");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride status is not Ended hence cannot start rating, status: "+ride.getRideStatus());
        }

        return ratingService.rateRider(ride, rating); // the rider is also in ride so no need to pass
    }

    @Override
    public DriverDto getMyProfile() {
        //getting the driver from the current driver
        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);
    }

    @Override
   public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        //why page bcz we have to return a lot of mata data along the list
        // return the list of all the rides returning the page request
        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageRequest).map( // this map method is from page library
                ride -> modelMapper.map(ride, RideDto.class) //converting the page into the list
        );
    }

    @Override
    public Driver getCurrentDriver() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with " +
                        "id "+user.getId()));
    }

    // updating the driver avalability
    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        driver.setAvailable(available);
        return driverRepository.save(driver);
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return driverRepository.save(driver);
    }

}