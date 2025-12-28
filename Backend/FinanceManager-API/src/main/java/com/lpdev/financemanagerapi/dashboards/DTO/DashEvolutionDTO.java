package com.lpdev.financemanagerapi.dashboards.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DashEvolutionDTO {

    private Integer month;
    private BigDecimal income;
    private BigDecimal expense;

    public DashEvolutionDTO(Integer month, BigDecimal income, BigDecimal expense) {
        this.month = month;
        this.income = income;
        this.expense = expense;
    }
}
