package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.BudgetRequest;
import com.yaswanth.budgetapp.dto.BudgetResponse;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.Budget;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.BudgetRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public BudgetResponse setBudget(BudgetRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Match existing budget by numeric month and year
        Optional<Budget> existingBudget = budgetRepository.findByUserIdAndMonth(
                user.getId(),
                request.month()
        );

        Budget budget = existingBudget.orElse(new Budget());
        budget.setAmount(request.amount());
        budget.setMonth(request.month());
        budget.setYear(request.year());
        budget.setUser(user);

        return mapToResponse(budgetRepository.save(budget));
    }

    @Override
    public List<BudgetResponse> getBudgetsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return budgetRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteBudget(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Budget budget = budgetRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found or unauthorized"));

        budgetRepository.delete(budget);
    }

    private BudgetResponse mapToResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getAmount(),
                budget.getMonth(),
                budget.getYear()
        );
    }
}