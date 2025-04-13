package com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiderDto {

    private int id;
    private UserDto user;
    private Double rating;
}
