package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.LoanRequest;
import com.yaswanth.budgetapp.dto.LoanResponse;
import com.yaswanth.budgetapp.dto.LoanPaymentRequest;

import java.util.List;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request, String email);

    List<LoanResponse> getLoansByUserEmail(String email);

    LoanResponse repay(Long loanId,
                       LoanPaymentRequest request,
                       String email);
}