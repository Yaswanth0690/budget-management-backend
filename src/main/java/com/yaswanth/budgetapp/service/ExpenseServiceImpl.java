package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.CategoryExpenseSummary;
import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.Category;
import com.yaswanth.budgetapp.model.Expense;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.CategoryRepository;
import com.yaswanth.budgetapp.repository.ExpenseRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository,
                              UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExpenseResponse createExpense(ExpenseRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new BusinessException("You cannot use another user's category");
        }

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .category(category)
                .user(user)
                .build();

        return mapToResponse(expenseRepository.save(expense));
    }

    @Override
    public Page<ExpenseResponse> getExpensesByUserEmail(String email,
                                                        Pageable pageable) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return expenseRepository.findByUser(user, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public List<CategoryExpenseSummary> getCategoryWiseSummary(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return expenseRepository.getCategoryWiseSummary(user);
    }

    @Override
    public ExpenseResponse updateExpense(Long id,
                                         ExpenseRequest request,
                                         String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Unauthorized access");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new BusinessException("You cannot use another user's category");
        }

        existing.setAmount(request.getAmount());
        existing.setDescription(request.getDescription());
        existing.setDate(request.getDate());
        existing.setCategory(category);

        return mapToResponse(expenseRepository.save(existing));
    }

    @Override
    public void deleteExpense(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Unauthorized access");
        }

        expenseRepository.delete(expense);
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getDate(),
                expense.getCategory().getName()
        );
    }
}