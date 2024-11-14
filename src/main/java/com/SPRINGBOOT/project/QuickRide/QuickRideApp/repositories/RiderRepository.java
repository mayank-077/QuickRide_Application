package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rider;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}