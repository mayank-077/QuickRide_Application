package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);

}