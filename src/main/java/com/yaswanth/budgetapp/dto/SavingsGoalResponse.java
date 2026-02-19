package com.yaswanth.budgetapp.dto;

public record SavingsGoalResponse(
        Long id,
        String goalName,
        Double targetAmount,
        Double savedAmount,
        Long userId
) {}
