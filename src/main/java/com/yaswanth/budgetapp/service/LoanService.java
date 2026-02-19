package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.LoanRequest;
import com.yaswanth.budgetapp.dto.LoanResponse;
import com.yaswanth.budgetapp.dto.LoanPaymentRequest;

import java.util.List;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request);

    List<LoanResponse> getLoansByUser(Long userId);

    LoanResponse repay(Long loanId, LoanPaymentRequest request);
}
