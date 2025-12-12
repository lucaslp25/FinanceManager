package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.model.enums.TransactionType;

import java.math.BigDecimal;

public record TransactionResponseDTO(

        String id,
        TransactionType transactionType,
        BigDecimal amount,
        BigDecimal newBalance,
        String message,
        Long userId
) {
    public TransactionResponseDTO(Transaction entity, Wallet wallet) {
        this(
                entity.getId(),
                entity.getTransactiontype(),
                entity.getAmount(),
                wallet.getBalance(),
                entity.getDescription(),
                wallet.getWalletOwner().getId()
        );
    }
}
