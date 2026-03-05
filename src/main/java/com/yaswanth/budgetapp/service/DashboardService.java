package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.DashboardResponse;

public interface DashboardService {
    DashboardResponse getDashboardSummary(int month, int year);
}