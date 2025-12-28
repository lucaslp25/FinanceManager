    package com.lpdev.financemanagerapi.dashboards.repositories;

    import com.lpdev.financemanagerapi.dashboards.DTO.DashAllocationResponseDTO;
    import com.lpdev.financemanagerapi.dashboards.DTO.DashEvolutionDTO;
    import com.lpdev.financemanagerapi.dashboards.DTO.DashTransactionDTO;
    import com.lpdev.financemanagerapi.dashboards.projections.DashboardSummaryProjection;
    import com.lpdev.financemanagerapi.model.entities.Transaction;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.Repository;
    import org.springframework.data.repository.query.Param;

    import java.util.List;

    // when extends the repository, turns this read-only (don´t have remove and save)
    public interface DashboardRepository extends Repository<Transaction, Long> {

        @Query(value = """
                    SELECT new com.lpdev.financemanagerapi.dashboards.DTO.DashAllocationResponseDTO(
                    v.categoryName,
                    SUM(v.total)
                )
                FROM DashboardAllocationView v
                GROUP BY v.categoryName
                ORDER BY SUM(v.total) DESC
                """)
        List<DashAllocationResponseDTO> getCategoryAllocationProjections(Pageable pageable);

        @Query("""
                SELECT new com.lpdev.financemanagerapi.dashboards.DTO.DashAllocationResponseDTO(
                    v.categoryName,
                    v.total
                )
                FROM DashboardAllocationView v
                WHERE v.a_year = :year AND v.a_month = :month
                ORDER BY v.total DESC
        """)
        List<DashAllocationResponseDTO> getCategoryAllocationProjectionsByMonth(@Param("year") Integer year, @Param("month") Integer month, Pageable pageable);


        @Query(value = "SELECT * FROM vw_dashboard_summary WHERE ano = :year AND mes = :month", nativeQuery = true)
        List<DashboardSummaryProjection> getDashboardSummaryProjectionsByMonth(@Param("year") Integer year, @Param("month") Integer month);


        @Query(value = "SELECT * FROM vw_dashboard_summary", nativeQuery = true)
        List<DashboardSummaryProjection> getAllDashboardSummaryProjections();

        @Query("""
               SELECT new com.lpdev.financemanagerapi.dashboards.DTO.DashEvolutionDTO(
                   CAST(EXTRACT(MONTH FROM t.date) AS INTEGER),
                   SUM(CASE WHEN t.transactiontype = 'DEPOSIT' THEN t.amount ELSE 0 END),
                   SUM(CASE WHEN t.transactiontype = 'WITHDRAW' THEN t.amount ELSE 0 END)
               )
               FROM Transaction t
               WHERE CAST(EXTRACT(YEAR FROM t.date) AS INTEGER) = :year
               GROUP BY EXTRACT(MONTH FROM t.date)
               ORDER BY EXTRACT(MONTH FROM t.date) ASC
               """)
        List<DashEvolutionDTO> getYearlyEvolution(@Param("year") Integer year);


        @Query(value = """
              SELECT new com.lpdev.financemanagerapi.dashboards.DTO.DashTransactionDTO(
                    c.name,
                    t.description,
                    t.amount,
                    t.date
                )
              FROM Transaction t
              JOIN t.withdrawCategory c
              WHERE t.transactiontype = 'WITHDRAW'
              ORDER BY t.amount DESC
        """)
        List<DashTransactionDTO> getTopExpensesTransactionsAllTime(Pageable pageable);


        @Query(value = """
              SELECT new com.lpdev.financemanagerapi.dashboards.DTO.DashTransactionDTO(
                    c.name,
                    t.description,
                    t.amount,
                    t.date
                )
              FROM Transaction t
              JOIN t.withdrawCategory c
              WHERE t.transactiontype = 'WITHDRAW'
              AND CAST(EXTRACT(YEAR FROM t.date) AS INTEGER) = :year
              AND CAST(EXTRACT(MONTH FROM t.date) AS INTEGER) = :month
              ORDER BY t.amount DESC
        """)
        List<DashTransactionDTO> getTopExpensesTransactionsByMonth
                (@Param("year") Integer year, @Param("month") Integer month, Pageable pageable);

    }