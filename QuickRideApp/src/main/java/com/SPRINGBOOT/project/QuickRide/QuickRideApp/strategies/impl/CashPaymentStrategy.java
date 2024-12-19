package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Payment;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentStatus;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.TransactionMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.PaymentRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.WalletService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider -> 100
//Driver -> 70 Deduct 30Rs from Driver's wallet

//here in cash all goes to driver but 30 rs is deducted from the driver's wallet
@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;


    @Override
    public void processPayment(Payment payment) {
        //form payment we are getting the rider and from that getting driver from that
        Driver driver = payment.getRide().getDriver();

        //deduction by company (commission)
        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        //deduct the money commission
        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);

        //saving payment status to confirm
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}

