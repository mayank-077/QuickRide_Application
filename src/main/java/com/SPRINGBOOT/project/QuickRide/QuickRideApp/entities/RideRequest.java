package com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import org.hibernate.annotations.CreationTimestamp;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        indexes = {
                @Index(name = "idx_ride_request_rider", columnList = "rider_id")
        }
)
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;

    @CreationTimestamp //automatically fill the requested time
    private LocalDateTime requestedtime;

    @ManyToOne(fetch = FetchType.LAZY) // One rider can have many riderequest
    private Rider rider;               //  Lazy: only when we are using the rider then only it will create the query and fetch the data

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;

    private Double fare;
}
