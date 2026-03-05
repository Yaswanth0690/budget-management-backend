package com.yaswanth.budgetapp.dto;

import java.util.List;

public record DashboardResponse(
        Double totalExpenses,
        Double totalBudgets,
        Double remainingBudget,
        Double totalRemainingLoans,
        Integer totalGoals,
        Integer totalCategories,
        Double totalGoalTargetAmount,
        Double totalGoalSavedAmount,
        List<CategoryExpenseSummary> categorySummary,
        List<SavingsGoalResponse> goals
) {}