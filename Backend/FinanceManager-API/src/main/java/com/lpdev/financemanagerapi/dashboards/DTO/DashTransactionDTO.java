package com.lpdev.financemanagerapi.dashboards.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@NoArgsConstructor
public class DashTransactionDTO {

    private String categoryName;
    private String description;
    private BigDecimal amount;
    private Instant date;

    public DashTransactionDTO(String categoryName, String description, BigDecimal amount, Instant date) {
        this.categoryName = categoryName;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
}
