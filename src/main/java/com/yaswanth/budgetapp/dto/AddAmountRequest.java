package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.Positive;

public record AddAmountRequest(

        @Positive(message = "Amount must be positive")
        Double amount
) {}
