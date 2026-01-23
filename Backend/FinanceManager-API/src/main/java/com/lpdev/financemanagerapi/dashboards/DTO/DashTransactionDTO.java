package com.lpdev.financemanagerapi.dashboards.DTO;

import com.lpdev.financemanagerapi.security.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashTransactionDTO {

    private String categoryName;
    private String description;
    private BigDecimal amount;
    private Instant date;

    public DashTransactionDTO(String categoryName, String description, BigDecimal amount, Instant date, User userId) {
        this.categoryName = categoryName;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }
}
