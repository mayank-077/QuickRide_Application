package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Payment;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRide(Ride ride);
}
