package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rating;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}