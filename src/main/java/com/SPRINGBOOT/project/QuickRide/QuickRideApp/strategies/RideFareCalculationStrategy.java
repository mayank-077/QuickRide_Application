package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideRequestDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;


public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);

}