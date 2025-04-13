package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RideRequestDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.RiderDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rider;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

//Services provided to the Rider
public interface RiderService {
    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
