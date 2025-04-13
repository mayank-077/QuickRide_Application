package com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto;

// Dto is data transfer to object
// works between two layer
// its function is to only transfer the data to objects
// no need to annotations

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private UserDto user;
    private Double rating;
}

