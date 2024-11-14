package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}