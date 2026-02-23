package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record BudgetRequest(

        @Positive(message = "Amount must be positive")
        Double amount,

        @NotBlank(message = "Month is required")
        String month
) {}