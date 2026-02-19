package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.AddAmountRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;

import java.util.List;

public interface SavingsGoalService {

    SavingsGoalResponse createGoal(SavingsGoalRequest request);

    List<SavingsGoalResponse> getGoalsByUser(Long userId);

    SavingsGoalResponse contribute(Long goalId, AddAmountRequest request);
}
