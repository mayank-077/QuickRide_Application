package com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PointDto {
    //it will contain the coordinate
    private double[] coordinates;
    private String type = "Point"; // the type is point


    public PointDto(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
