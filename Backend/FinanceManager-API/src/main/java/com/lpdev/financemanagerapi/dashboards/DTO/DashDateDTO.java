package com.lpdev.financemanagerapi.dashboards.DTO;

import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DashDateDTO {

    @Nullable
    Integer year;

    @Nullable
    Integer month;

    public DashDateDTO(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }
}
