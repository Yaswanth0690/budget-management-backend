package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.CategoryExpenseSummary;
import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse createExpense(ExpenseRequest request, String email);

    Page<ExpenseResponse> getExpensesByUserEmail(String email, Pageable pageable);

    ExpenseResponse updateExpense(Long id, ExpenseRequest request, String email);

    List<CategoryExpenseSummary> getCategoryWiseSummary(String email);

    void deleteExpense(Long id, String email);
}