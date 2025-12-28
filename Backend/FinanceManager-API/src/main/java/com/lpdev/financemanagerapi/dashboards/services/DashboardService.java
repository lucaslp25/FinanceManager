package com.lpdev.financemanagerapi.dashboards.services;

import com.lpdev.financemanagerapi.dashboards.DTO.*;
import com.lpdev.financemanagerapi.dashboards.projections.DashboardSummaryProjection;
import com.lpdev.financemanagerapi.dashboards.repositories.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    // using the dto
    @Transactional(readOnly = true)
    public List<DashAllocationResponseDTO> getAllocation(DashDateDTO dto){

        List<DashAllocationResponseDTO> response;

        Pageable top5 = PageRequest.of(0, 5);

        // if the user use the filter, make the request using the mouth filter.
        if (dto.getYear() != null && dto.getMonth() != null){
            response = dashboardRepository.getCategoryAllocationProjectionsByMonth(dto.getYear(), dto.getMonth(), top5);
        } else {
            response = dashboardRepository.getCategoryAllocationProjections(top5);
        }

        System.out.println(response);
        return response;
    }

    // using projections
    @Transactional(readOnly = true)
    public List<DashSummaryResponseDTO> getSummary(DashDateDTO dto){

        List<DashboardSummaryProjection> response;

        if (dto.getYear() != null && dto.getMonth() != null) {
            response = dashboardRepository.getDashboardSummaryProjectionsByMonth(dto.getYear(), dto.getMonth());
        }else {
            response = dashboardRepository.getAllDashboardSummaryProjections();
        }

        System.out.println(response);
        return response.stream().map(DashSummaryResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DashEvolutionDTO> getEvolution(Integer year) {

        if (year == null) year = LocalDate.now().getYear();

        List<DashEvolutionDTO> rawData = dashboardRepository.getYearlyEvolution(year);
        List<DashEvolutionDTO> fullData = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            final int currentMonth = i;
            DashEvolutionDTO monthData = rawData.stream().
                    filter(d -> d.getMonth() == currentMonth)
                    .findFirst()
                    .orElse(new DashEvolutionDTO(currentMonth, BigDecimal.ZERO, BigDecimal.ZERO));

            fullData.add(monthData);
        }
        System.out.println(fullData);
        return fullData;
    }

    @Transactional(readOnly = true)
    public List<DashTransactionDTO> getTopExpenseTransactions(DashDateDTO dto, Integer size){

        List<DashTransactionDTO> response;

        if (size == null) size = 5;

        Pageable top = PageRequest.of(0, size);

        if (dto.getYear() != null && dto.getMonth() != null){
            response = dashboardRepository.getTopExpensesTransactionsByMonth(dto.getYear(), dto.getMonth(), top);
        } else {
            response = dashboardRepository.getTopExpensesTransactionsAllTime(top);
        }
        // debug
        System.out.println(response);
        return response;
    }

}
