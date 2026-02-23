package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.BudgetRequest;
import com.yaswanth.budgetapp.dto.BudgetResponse;

import java.util.List;

public interface BudgetService {

    BudgetResponse setBudget(BudgetRequest request, String email);

    List<BudgetResponse> getBudgetsByUserEmail(String email);

    void deleteBudget(Long id, String email);
}