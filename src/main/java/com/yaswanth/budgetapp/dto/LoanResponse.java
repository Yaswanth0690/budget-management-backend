package com.yaswanth.budgetapp.dto;

public record LoanResponse(
        Long id,
        Double totalAmount,
        Double remainingAmount,
        Double interestRate,
        Long userId
) {}
