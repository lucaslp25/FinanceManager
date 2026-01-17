package com.lpdev.financemanagerapi.DTO;

import com.lpdev.financemanagerapi.model.entities.Goal;
import com.lpdev.financemanagerapi.model.enums.GoalPriority;
import com.lpdev.financemanagerapi.model.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record GoalResponseDTO(

        Long id,
        String name,
        String description,
        BigDecimal targetAmount,
        Instant deadline,
        Instant initDate,
        Double progressPercentage,
        BigDecimal suggestedMonthlyDeposit,
        GoalPriority goalPriority,
        GoalStatus goalStatus,
        BigDecimal remainingPay,
        BigDecimal currentAmount
) {

    public GoalResponseDTO(Goal entity){
        this(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getTargetAmount(),
                entity.getDeadline(),
                entity.getInitDate(),
                entity.getCurrentPercentage(),
                entity.getSuggestedMonthlyDeposit(),
                entity.getGoalPriority(),
                entity.getGoalStatus(),
                entity.getRemainingPay(),
                entity.getCurrentAmount()
        );
    }
}
