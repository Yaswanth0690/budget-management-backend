package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.Category;
import com.yaswanth.budgetapp.model.Expense;
import com.yaswanth.budgetapp.repository.CategoryRepository;
import com.yaswanth.budgetapp.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    // ================= CREATE =================
    @Override
    public ExpenseResponse addExpense(ExpenseRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id "
                                + request.getCategoryId()));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setDate(request.getDate());
        expense.setCategory(category);

        Expense saved = expenseRepository.save(expense);

        return mapToResponse(saved);
    }

    // ================= GET BY ID =================
    @Override
    public ExpenseResponse getExpenseById(Long id) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        return mapToResponse(expense);
    }

    // ================= PAGINATION + SORTING =================
    @Override
    public Page<ExpenseResponse> getAllExpenses(int page,
                                                int size,
                                                String sortBy,
                                                String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Expense> expensePage = expenseRepository.findAll(pageable);

        return expensePage.map(this::mapToResponse);
    }

    // ================= UPDATE =================
    @Override
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {

        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found with id " + id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id "
                                + request.getCategoryId()));

        existing.setAmount(request.getAmount());
        existing.setDescription(request.getDescription());
        existing.setDate(request.getDate());
        existing.setCategory(category);

        Expense updated = expenseRepository.save(existing);

        return mapToResponse(updated);
    }

    // ================= DELETE =================
    @Override
    public void deleteExpense(Long id) {

        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id " + id);
        }

        expenseRepository.deleteById(id);
    }

    // ================= MAPPING METHOD =================
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
