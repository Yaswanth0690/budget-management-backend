package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BudgetRequest(

        @NotNull(message = "User ID is required")
        Long userId,

        @Positive(message = "Amount must be positive")
        Double amount,

        @NotNull(message = "Month is required")
        String month
) {}
