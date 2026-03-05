package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.DashboardResponse;
import com.yaswanth.budgetapp.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public DashboardResponse getDashboardSummary(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        int targetMonth = (month != null) ? month : LocalDate.now().getMonthValue();
        int targetYear = (year != null) ? year : LocalDate.now().getYear();

        return dashboardService.getDashboardSummary(targetMonth, targetYear);
    }
}