package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanRequest(

        @NotNull(message = "User ID is required")
        Long userId,

        @Positive(message = "Total amount must be positive")
        Double totalAmount,

        @Positive(message = "Interest rate must be positive")
        Double interestRate
) {}
