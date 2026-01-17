package com.lpdev.financemanagerapi.DTO;

import java.math.BigDecimal;

public record GoalSaveMoneyDTO(

        BigDecimal amount
) {
    public GoalSaveMoneyDTO(BigDecimal amount){
        this.amount = amount;
    }
}
