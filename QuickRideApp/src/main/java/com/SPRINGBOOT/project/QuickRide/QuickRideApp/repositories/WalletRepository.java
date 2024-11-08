package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.User;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
