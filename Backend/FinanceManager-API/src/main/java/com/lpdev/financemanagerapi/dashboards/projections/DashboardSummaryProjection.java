package com.lpdev.financemanagerapi.dashboards.projections;

import java.math.BigDecimal;

public interface DashboardSummaryProjection {

    Integer getMes();
    Integer getAno();
    BigDecimal getTotalDeposit();
    BigDecimal getTotalWithdraw();
}
