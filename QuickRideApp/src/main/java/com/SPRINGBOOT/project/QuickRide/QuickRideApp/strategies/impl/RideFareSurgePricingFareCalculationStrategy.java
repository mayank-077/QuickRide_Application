package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl;

// cal on the basis of sure multiply the fare by anything or for the hype

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideRequestDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.DistanceService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
