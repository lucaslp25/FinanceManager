package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Goal;

import java.math.BigDecimal;
import java.time.Instant;

public record GoalResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal targetAmount,
        Instant deadline,
        Instant initDate

) {

    public GoalResponseDTO(Goal entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTargetAmount(),
                entity.getDeadline(),
                entity.getInitDate()
        );
    }
}
