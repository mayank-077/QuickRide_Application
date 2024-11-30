package com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.TransactionMethod;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.TransactionType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDto {

    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private WalletDto wallet;

    private LocalDateTime timeStamp;
}
