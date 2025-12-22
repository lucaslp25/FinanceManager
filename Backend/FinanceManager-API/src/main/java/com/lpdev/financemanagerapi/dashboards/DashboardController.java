package com.lpdev.financemanagerapi.dashboards;

import com.lpdev.financemanagerapi.dashboards.DTO.DashAllocationResponseDTO;
import com.lpdev.financemanagerapi.dashboards.DTO.DashSummaryDTO;
import com.lpdev.financemanagerapi.dashboards.DTO.DashSummaryResponseDTO;
import com.lpdev.financemanagerapi.dashboards.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(value = "/summary")
    public ResponseEntity<List<DashSummaryResponseDTO>> getSummary(@ModelAttribute DashSummaryDTO dto){
        List<DashSummaryResponseDTO> response = dashboardService.getSummary(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/category-allocation")
    public ResponseEntity<List<DashAllocationResponseDTO>> getAllocation(){
        List<DashAllocationResponseDTO> response = dashboardService.getAllocation();
        return ResponseEntity.ok().body(response);
    }

}