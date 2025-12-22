package com.lpdev.financemanagerapi.dashboards.DTO;

import com.lpdev.financemanagerapi.dashboards.projections.DashboardSummaryProjection;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashSummaryResponseDTO {
    private Integer ano;
    private Integer mes;
    private BigDecimal totalDeposit;
    private BigDecimal totalWithdraw;

    public DashSummaryResponseDTO(){}

    public DashSummaryResponseDTO(DashboardSummaryProjection projection){
        this.ano = projection.getAno();
        this.mes = projection.getMes();
        this.totalDeposit = projection.getTotalDeposit();
        this.totalWithdraw = projection.getTotalWithdraw();
    }
}

