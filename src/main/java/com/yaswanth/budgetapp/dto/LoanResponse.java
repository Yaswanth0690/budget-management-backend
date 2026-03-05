package com.yaswanth.budgetapp.dto;

public record LoanResponse(
        Long id,
        String loanName,
        Double principalAmount,
        Double interestRate,
        Double totalAmount,
        Double paidAmount,
        Double remainingAmount,
        Long userId
) {}