package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record BalanceDTO(
        @NotNull(message = "The amount cannot be null.")
        BigDecimal amount,

        @NotNull(message = "The category cannot be null.")
        Long categoryId,

        @Nullable
        String description,

        @Nullable
        Instant date
) {
    public BalanceDTO(Transaction entity){
        this(entity.getAmount(), entity.getDepositCategory().getId(), entity.getDescription(), entity.getDate());
    }
}