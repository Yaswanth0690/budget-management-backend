package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record LoanRequest(

        @NotBlank(message = "Loan name is required")
        String loanName,

        @Positive(message = "Principal amount must be positive")
        Double principalAmount,

        @Positive(message = "Interest rate must be positive")
        Double interestRate
) {}