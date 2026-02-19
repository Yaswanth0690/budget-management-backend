package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.Positive;

public record LoanPaymentRequest(

        @Positive(message = "Payment amount must be positive")
        Double amount
) {}
