package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Payment;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Rider;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentStatus;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.TransactionMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.PaymentRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.WalletService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider had 232, Driver had 500
//Ride cost is 100, commission = 30
//Rider -> 232-100 = 132
//Driver -> 500 + (100 - 30) = 570

@Service
@RequiredArgsConstructor
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    //to break the circular dependency b/w paymentservice impl
    //                                     -> paymentStrategyManager
    //                                     -> cashpaymentStrategy
    //                                     ->back to paymentservice

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(),
                payment.getAmount(), null, payment.getRide(), TransactionMethod.RIDE);

        //amount driver gets
        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(),
                driversCut, null, payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}
