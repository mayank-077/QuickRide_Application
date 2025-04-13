package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies;

// Have all Buisness logic

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideRequestDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    List<Driver> findMatchingDriver(RideRequest rideRequest);
    // return the list of all the matched drivers
}
