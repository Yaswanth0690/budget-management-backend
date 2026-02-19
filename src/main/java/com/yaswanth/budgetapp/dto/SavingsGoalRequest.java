package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SavingsGoalRequest(

        @NotNull(message = "User ID is required")
        Long userId,

        @NotBlank(message = "Goal name is required")
        String goalName,

        @Positive(message = "Target amount must be positive")
        Double targetAmount
) {}

