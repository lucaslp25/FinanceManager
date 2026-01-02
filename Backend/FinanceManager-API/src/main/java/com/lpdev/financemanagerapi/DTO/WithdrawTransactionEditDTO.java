package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record WithdrawTransactionEditDTO(

        @Nullable
        BigDecimal amount,

        @Nullable
        String description,

        @Nullable
        Long categoryId,

        @Nullable
        Instant date
) {

    public WithdrawTransactionEditDTO(Transaction entity){
        this(entity.getAmount(), entity.getDescription(), entity.getWithdrawCategory().getId(), entity.getDate());
    }

}
