package com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = { //for optimizing the schema so it will create a small table inside this table for fast searching
        @Index(name = "idx_driver_vehicle_id", columnList = "vehicleId") // so the searching is very fast
})
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;

    private Boolean available;

    private String vehicleId;

    @Column(columnDefinition = "Geometry(Point, 4326)") // Column definition type and 4326 is earth geometry
    private Point currentLocation;

}
