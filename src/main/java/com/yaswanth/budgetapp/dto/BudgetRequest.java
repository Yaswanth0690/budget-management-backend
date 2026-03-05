package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BudgetRequest(
        @NotNull @Positive Double amount,
        @NotNull @Min(1) @Max(12) Integer month,
        @NotNull @Min(2000) Integer year
) {}