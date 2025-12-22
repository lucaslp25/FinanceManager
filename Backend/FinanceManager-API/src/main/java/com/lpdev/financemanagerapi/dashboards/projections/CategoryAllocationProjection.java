package com.lpdev.financemanagerapi.dashboards.projections;

import java.math.BigDecimal;

//  projection class it´s a much lighter class for performing read-only searches in a database
public interface CategoryAllocationProjection {

    // the methods will be receiving the name of columns of my view (ignoring cases)
    String getName();
    BigDecimal getTotal();
}
