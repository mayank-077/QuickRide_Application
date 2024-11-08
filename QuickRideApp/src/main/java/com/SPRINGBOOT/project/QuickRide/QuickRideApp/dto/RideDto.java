package com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideDto {

    private Long id;
    private Point pickupLocation;
    private Point dropOffLocation;

    private LocalDateTime createdTime;
    private RiderDto rider;
    private DriverDto driver;
    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}