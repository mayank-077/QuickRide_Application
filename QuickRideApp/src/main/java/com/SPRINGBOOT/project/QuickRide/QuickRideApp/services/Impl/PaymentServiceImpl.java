package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.Impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Payment;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Ride;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentStatus;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.exceptions.ResourceNotFoundException;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.PaymentRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.PaymentService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;

    //passing to the payment strategy
    @Override
    public void processPayment(Ride ride) {
        //calling the payment strategy manager
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride with id: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(payment);
    }

    @Override
    public Payment createNewPayment(Ride ride) {
        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethod(ride.getPaymentMethod())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        return paymentRepository.save(payment);
    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus status) {
        payment.setPaymentStatus(status);
        paymentRepository.save(payment);
    }
}
