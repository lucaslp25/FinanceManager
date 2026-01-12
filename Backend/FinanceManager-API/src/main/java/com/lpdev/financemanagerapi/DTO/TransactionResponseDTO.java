package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.Wallet;
import com.lpdev.financemanagerapi.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponseDTO(

        String transactionId,
        Instant date,
        String categoryName,
        TransactionType transactionType,
        BigDecimal amount,
        BigDecimal newBalance,
        String description,
        Long userId,
        Long categoryId
) {
    public TransactionResponseDTO(Transaction entity, Wallet wallet) {
        this(
                entity.getId(),
                entity.getDate(),

                entity.getTransactiontype() ==
                        TransactionType.DEPOSIT ? entity.getDepositCategory().getName() :
                        entity.getWithdrawCategory().getName(),

                entity.getTransactiontype(),
                entity.getAmount(),
                wallet.getBalance(),
                entity.getDescription(),
                wallet.getWalletOwner().getId(),

                entity.getTransactiontype() ==
                        TransactionType.DEPOSIT ? entity.getDepositCategory().getId() :
                        entity.getWithdrawCategory().getId()
        );
    }
}