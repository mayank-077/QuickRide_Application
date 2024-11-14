package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

// it is the strategy manager and manage the different strategy
// it will decide the strategy

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;
    private final RiderFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {
        //if rider rating is good they get good rated drivers otherwise nearest drivers
        if(riderRating >= 4.8) {
            return highestRatedDriverStrategy;
        } else {
            return nearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {

//        6PM to 9PM is SURGE TIME
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime) {
            return surgePricingFareCalculationStrategy;
        } else {
            return defaultFareCalculationStrategy;
        }
    }

}