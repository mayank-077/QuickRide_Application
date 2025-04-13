package com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories;


import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
}
