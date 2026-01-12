package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Transaction;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionEditDTO(

        @Nullable
        BigDecimal amount,

        @Nullable
        String description,

        @Nullable
        Long categoryId,

        @Nullable
        Instant date
) {

    public TransactionEditDTO(Transaction entity){
        this(entity.getAmount(), entity.getDescription(), entity.getWithdrawCategory().getId(), entity.getDate());
    }

}
