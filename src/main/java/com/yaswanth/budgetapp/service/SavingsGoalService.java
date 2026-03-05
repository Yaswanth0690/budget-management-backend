package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.AddAmountRequest;
import com.yaswanth.budgetapp.dto.ExtendGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;

import java.util.List;

public interface SavingsGoalService {

    SavingsGoalResponse createGoal(SavingsGoalRequest request, String email);

    List<SavingsGoalResponse> getGoalsByUserEmail(String email);

    SavingsGoalResponse contribute(Long goalId,
                                   AddAmountRequest request,
                                   String email);

    SavingsGoalResponse extendGoal(Long goalId,
                                   ExtendGoalRequest request,
                                   String email);

    void deleteGoal(Long id, String email);
}