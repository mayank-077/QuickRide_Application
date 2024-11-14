package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
