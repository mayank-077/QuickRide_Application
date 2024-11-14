package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RiderDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;

public interface RatingService {

    //why driverdto bcz we want that after rating one can se the overall rating
    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride ride, Integer rating);

    void createNewRating(Ride ride);
}