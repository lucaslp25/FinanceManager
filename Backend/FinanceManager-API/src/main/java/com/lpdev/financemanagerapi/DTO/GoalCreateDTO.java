package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Goal;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;
import java.time.Instant;

public record GoalCreateDTO(

        String name,

        @Nullable
        String description,

        BigDecimal targetAmount,

        @Nullable
        Instant deadline
) {

    public GoalCreateDTO(Goal entity){
        this(
                entity.getName(),
                entity.getDescription(),
                entity.getTargetAmount(),
                entity.getDeadline()
        );
    }
}
