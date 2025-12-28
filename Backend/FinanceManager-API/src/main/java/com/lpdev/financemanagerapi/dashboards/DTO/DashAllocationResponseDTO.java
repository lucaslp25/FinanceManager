package com.lpdev.financemanagerapi.dashboards.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DashAllocationResponseDTO{

    private String name;
    private BigDecimal total;

    public DashAllocationResponseDTO(String name, BigDecimal total){
        this.name = name;
        this.total = total;
    }
}
