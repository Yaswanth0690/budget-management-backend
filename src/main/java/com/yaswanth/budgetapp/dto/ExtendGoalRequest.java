package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExtendGoalRequest(
        @NotNull(message = "Target amount is required")
        @Positive(message = "Target amount must be positive")
        Double targetAmount
) {}