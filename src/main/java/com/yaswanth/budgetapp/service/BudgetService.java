package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.BudgetRequest;
import com.yaswanth.budgetapp.dto.BudgetResponse;

public interface BudgetService {

    BudgetResponse setBudget(BudgetRequest request);

    BudgetResponse getBudgetByUserAndMonth(Long userId, String month);

    void deleteBudget(Long id);
}
