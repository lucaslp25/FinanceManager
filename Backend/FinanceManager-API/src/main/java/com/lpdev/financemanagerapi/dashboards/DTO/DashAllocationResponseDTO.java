package com.lpdev.financemanagerapi.dashboards.DTO;

import com.lpdev.financemanagerapi.dashboards.projections.CategoryAllocationProjection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashAllocationResponseDTO{

    private String name;
    private BigDecimal total;

    public DashAllocationResponseDTO() {}

    public DashAllocationResponseDTO(CategoryAllocationProjection projection){
        this.name = projection.getName();
        this.total = projection.getTotal();
    }
}
