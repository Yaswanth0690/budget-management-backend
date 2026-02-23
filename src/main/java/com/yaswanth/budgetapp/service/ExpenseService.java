package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {

    ExpenseResponse createExpense(ExpenseRequest request, String email);

    Page<ExpenseResponse> getExpensesByUserEmail(String email, Pageable pageable);

    ExpenseResponse updateExpense(Long id, ExpenseRequest request, String email);

    void deleteExpense(Long id, String email);
}