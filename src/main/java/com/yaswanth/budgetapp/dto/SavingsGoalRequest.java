package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SavingsGoalRequest(

        @NotBlank(message = "Goal name is required")
        String goalName,

        @Positive(message = "Target amount must be positive")
        Double targetAmount
) {}
