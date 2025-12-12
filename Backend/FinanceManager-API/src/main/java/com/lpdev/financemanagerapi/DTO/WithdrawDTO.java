package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import com.lpdev.financemanagerapi.model.entities.WithdrawCategory;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record WithdrawDTO(

        @NotNull(message = "The amount cannot be null.")
        BigDecimal amount,

        @NotNull(message = "The category cannot be null.")
        Long categoryId,

        @Nullable
        String description
) {
    public WithdrawDTO(Transaction entity){
        this(
                entity.getAmount(),
                entity.getWithdrawCategory().getId(),
                entity.getDescription()
        );
    }
}
