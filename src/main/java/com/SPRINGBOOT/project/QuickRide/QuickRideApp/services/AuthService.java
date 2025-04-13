package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.SignupDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.UserDto;

// WHY interfaces to achieve the losscoupling and readability
//loss coupling to acheve the same method and another way to implement it
// make scalable

// Deal with th login authentication services
public interface AuthService {

    String[] login(String email, String password);

    UserDto signup(SignupDto signupDto);

    DriverDto onboardNewDriver(Long userId,  String vehicleId); // making new driver

    String refreshToken(String refreshToken);
}
