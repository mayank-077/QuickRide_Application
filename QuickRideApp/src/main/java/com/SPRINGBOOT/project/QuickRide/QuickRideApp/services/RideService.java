package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideRequestDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rider;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

// Services by the ride
public interface RideService {
    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    // return data in pageformate type page number and

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);


}
