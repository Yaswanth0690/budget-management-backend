package com.yaswanth.budgetapp.dto;

public record BudgetResponse(
        Long id,
        Double amount,
        String month
) {}