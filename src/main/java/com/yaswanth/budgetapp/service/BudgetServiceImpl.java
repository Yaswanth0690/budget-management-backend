package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.BudgetRequest;
import com.yaswanth.budgetapp.dto.BudgetResponse;
import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.Budget;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.BudgetRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository,
                             UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BudgetResponse setBudget(BudgetRequest request) {

        if (budgetRepository.findByUserIdAndMonth(
                request.userId(), request.month()).isPresent()) {

            throw new BusinessException("Budget already exists for this month");
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Budget budget = Budget.builder()
                .amount(request.amount())
                .month(request.month())
                .user(user)
                .build();

        return mapToResponse(budgetRepository.save(budget));
    }


    @Override
    public BudgetResponse getBudgetByUserAndMonth(Long userId, String month) {

        Budget budget = budgetRepository
                .findByUserIdAndMonth(userId, month)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));

        return mapToResponse(budget);
    }

    @Override
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found");
        }
        budgetRepository.deleteById(id);
    }

    private BudgetResponse mapToResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getAmount(),
                budget.getMonth(),
                budget.getUser().getId()
        );
    }
}
