package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.Impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.WalletTransaction;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.WalletTransactionRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        //saving the
        walletTransactionRepository.save(walletTransaction);
    }

}