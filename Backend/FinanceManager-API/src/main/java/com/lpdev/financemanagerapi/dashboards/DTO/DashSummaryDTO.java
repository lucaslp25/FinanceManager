package com.lpdev.financemanagerapi.dashboards.DTO;

import lombok.Data;

@Data
public class DashSummaryDTO {

    Integer year;
    Integer month;

    public DashSummaryDTO () {}

    public DashSummaryDTO(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }
}
