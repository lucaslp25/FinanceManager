package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public record WithdrawTransactionResponseDTO(
        String transactionId,
        Instant date,
        BigDecimal amount,
        String categoryName,
        String description,
        Long categoryId
) {
    public WithdrawTransactionResponseDTO(Transaction entity){
        this(entity.getId(), entity.getDate(), entity.getAmount(), entity.getWithdrawCategory().getName(), entity.getDescription(), entity.getWithdrawCategory().getId());
    }
}
