package com.lpdev.financemanagerapi.dashboards.services;

import com.lpdev.financemanagerapi.dashboards.DTO.DashAllocationResponseDTO;
import com.lpdev.financemanagerapi.dashboards.DTO.DashSummaryDTO;
import com.lpdev.financemanagerapi.dashboards.DTO.DashSummaryResponseDTO;
import com.lpdev.financemanagerapi.dashboards.projections.CategoryAllocationProjection;
import com.lpdev.financemanagerapi.dashboards.projections.DashboardSummaryProjection;
import com.lpdev.financemanagerapi.dashboards.repositories.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    @Transactional(readOnly = true)
    public List<DashAllocationResponseDTO> getAllocation(){
        List<CategoryAllocationProjection> categories = dashboardRepository.getCategoryAllocationProjections();
        return categories.stream().map(DashAllocationResponseDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DashSummaryResponseDTO> getSummary(DashSummaryDTO dto){
        List<DashboardSummaryProjection> summary = dashboardRepository.
                getDashboardSummaryProjections(dto.getYear(), dto.getMonth());
        return summary.stream().map(DashSummaryResponseDTO::new).collect(Collectors.toList());
    }

}
