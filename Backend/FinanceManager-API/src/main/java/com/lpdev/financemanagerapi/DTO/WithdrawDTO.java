package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public record WithdrawDTO(

        @NotNull(message = "The amount cannot be null.")
        BigDecimal amount,

        @NotNull(message = "The category cannot be null.")
        Long categoryId,

        @Nullable
        String description,

        @Nullable
        Instant date
) {
    public WithdrawDTO(Transaction entity){
        this(
                entity.getAmount(),
                entity.getWithdrawCategory().getId(),
                entity.getDescription(),
                entity.getDate()
        );
    }
}
