package com.lpdev.financemanagerapi.dashboards.controllers;

import com.lpdev.financemanagerapi.dashboards.DTO.*;
import com.lpdev.financemanagerapi.dashboards.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(value = "/summary")
    public ResponseEntity<List<DashSummaryResponseDTO>> getSummary(@ModelAttribute DashDateDTO dto){
        List<DashSummaryResponseDTO> response = dashboardService.getSummary(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/category-allocation")
    public ResponseEntity<List<DashAllocationResponseDTO>> getAllocation(DashDateDTO dto){
        List<DashAllocationResponseDTO> response = dashboardService.getAllocation(dto);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/evolution")
    public ResponseEntity<List<DashEvolutionDTO>> getEvolution(@RequestParam(value = "year", required = false) Integer year){
        List<DashEvolutionDTO> response = dashboardService.getEvolution(year);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/top-expenses")
    public ResponseEntity<List<DashTransactionDTO>> getTopExpenses
            (@ModelAttribute DashDateDTO dto, @RequestParam(value = "size", defaultValue = "5") Integer size){
        List<DashTransactionDTO> response = dashboardService.getTopExpenseTransactions(dto, size);
        return ResponseEntity.ok().body(response);
    }

}