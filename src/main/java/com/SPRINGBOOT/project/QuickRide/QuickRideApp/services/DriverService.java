package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;


import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RiderDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

//what a driver can do(services provided to the drivers)
public interface DriverService {
    RideDto acceptRide(Long rideRequestId);  // return the RideDto type

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver, boolean available);

    Driver createNewDriver(Driver driver);
}
