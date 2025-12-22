package com.lpdev.financemanagerapi.dashboards.repositories;

import com.lpdev.financemanagerapi.dashboards.projections.CategoryAllocationProjection;
import com.lpdev.financemanagerapi.dashboards.projections.DashboardSummaryProjection;
import com.lpdev.financemanagerapi.model.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// when extends the repository, turns this read-only (don´t have remove and save)
public interface DashboardRepository extends Repository<Transaction, Long> {

    @Query(value = "SELECT * FROM vw_dashboard_category_allocation", nativeQuery = true)
    List<CategoryAllocationProjection> getCategoryAllocationProjections();

    @Query(value = "SELECT * FROM vw_dashboard_summary WHERE ano = :year AND mes = :month", nativeQuery = true)
    List<DashboardSummaryProjection> getDashboardSummaryProjections(@Param("year") Integer year, @Param("month") Integer month);

}
