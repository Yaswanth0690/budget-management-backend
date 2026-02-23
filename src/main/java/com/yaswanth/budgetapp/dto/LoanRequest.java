package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.Positive;

public record LoanRequest(

        @Positive(message = "Total amount must be positive")
        Double totalAmount,

        @Positive(message = "Interest rate must be positive")
        Double interestRate
) {}
