package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.Impl;


import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RiderDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rating;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rider;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.exceptions.ResourceNotFoundException;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.exceptions.RuntimeConflictException;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.DriverRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.RatingRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.RiderRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Driver driver = ride.getDriver();

        //as soon the ride start it will create a rating object
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id: "+ride.getId()));

        if(ratingObj.getDriverRating() != null)
            throw new RuntimeConflictException("Driver has already been rated, cannot rate again");

        ratingObj.setDriverRating(rating);

        ratingRepository.save(ratingObj);

        //getting all the ratings of driver
        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0); // calculating the average
        driver.setRating(newRating); // update the new rating

        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id: "+ride.getId()));

        //once rider rated he cannot rate again
        if(ratingObj.getRiderRating() != null)
            throw new RuntimeConflictException("Rider has already been rated, cannot rate again");

        ratingObj.setRiderRating(rating);

        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);
        rider.setRating(newRating);

        Rider savedRider = riderRepository.save(rider);
        return modelMapper.map(savedRider, RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
//10 ratingsCount -> 4.0
//new rating 4.6
//updated rating
//new rating 44.6/11 -> 4.05