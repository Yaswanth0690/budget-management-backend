package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.CategoryExpenseSummary;
import com.yaswanth.budgetapp.dto.DashboardResponse;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.SavingsGoal;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final SavingsGoalRepository goalRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(ExpenseRepository expenseRepository,
                                BudgetRepository budgetRepository,
                                SavingsGoalRepository goalRepository,
                                LoanRepository loanRepository,
                                UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
        this.goalRepository = goalRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DashboardResponse getDashboardSummary(int month, int year) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Long userId = user.getId();

        // 1. Monthly Filtered Data
        Double totalExpenses = expenseRepository.sumTotalAmountByMonth(user, month, year);
        Double totalBudgets = budgetRepository.sumAmountByUserIdAndMonth(userId, month, year);
        List<CategoryExpenseSummary> categorySummary = expenseRepository.getCategoryWiseSummaryByMonth(user, month, year);

        // 2. Savings Goals Logic
        List<SavingsGoal> goals = goalRepository.findByUser(user);

        // Count how many goals are 100% completed
        long completedGoals = goals.stream()
                .filter(g -> g.getSavedAmount() >= g.getTargetAmount())
                .count();

        // Map the entities to Response DTOs
        List<SavingsGoalResponse> goalResponses = goals.stream()
                .map(g -> new SavingsGoalResponse(
                        g.getId(),
                        g.getGoalName(),
                        g.getTargetAmount(),
                        g.getSavedAmount(),
                        user.getId()
                ))
                .collect(Collectors.toList());

        Double target = goalRepository.sumTargetAmountByUser(user);
        Double saved = goalRepository.sumSavedAmountByUser(user);

        // 3. Loans Logic
        Double remainingLoans = loanRepository.sumRemainingAmountByUser(user);

        // Null checks
        double expensesVal = totalExpenses != null ? totalExpenses : 0.0;
        double budgetsVal = totalBudgets != null ? totalBudgets : 0.0;
        double targetVal = target != null ? target : 0.0;
        double savedVal = saved != null ? saved : 0.0;
        double loansVal = remainingLoans != null ? remainingLoans : 0.0;

        return new DashboardResponse(
                expensesVal,
                budgetsVal,
                budgetsVal - expensesVal,
                loansVal,
                (int) completedGoals,  // 🔥 Re-purposed to show "Completed" count
                4,
                targetVal,
                savedVal,
                categorySummary,
                goalResponses          // 🔥 Passed full list of goals
        );
    }
}