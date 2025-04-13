package com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.PaymentMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl.CashPaymentStrategy;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}