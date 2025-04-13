package com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideRequestStatus;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = { // for searching of all rides
        //first goes to index then find the location for specific then search at location
        @Index(name = "idx_ride_rider", columnList = "rider_id"),
        @Index(name = "idx_ride_driver", columnList = "driver_id")
})
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;

    @CreationTimestamp //automatically fill the current time
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY) // One rider can have many riderequest
    private Rider rider;               //  Fetch: only when we are using the rider then only it will create the query and fetch the data

    @ManyToOne(fetch = FetchType.LAZY) // One driver can have many ride
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
