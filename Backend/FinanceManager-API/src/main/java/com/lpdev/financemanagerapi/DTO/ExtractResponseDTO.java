package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.enums.TransactionType;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record ExtractResponseDTO(
        String transactionId,
        Instant date,
        BigDecimal amount,

        @Nullable
        String categoryName,

        String description,

        @Nullable
        Long categoryId,

        TransactionType transactionType
) {

    public ExtractResponseDTO(Transaction entity){
        this(
                entity.getId(),
                entity.getDate(),
                entity.getAmount(),
                entity.getWithdrawCategory() != null ? entity.getWithdrawCategory().getName() : "Sem categoria",
                entity.getDescription(),
                entity.getWithdrawCategory() != null ? entity.getWithdrawCategory().getId() : null,
                entity.getTransactiontype()
        );
    }

}
