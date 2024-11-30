package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;


import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {  // long is the id type so it will search for the riderequest and the id for it

}