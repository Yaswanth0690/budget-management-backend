package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.model.Expense;
import com.yaswanth.budgetapp.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseServiceImpl(ExpenseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    @Override
    public void deleteExpense(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Expense updateExpense(Long id, Expense updatedExpense) {

        Expense existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

        existing.setAmount(updatedExpense.getAmount());
        existing.setDescription(updatedExpense.getDescription());
        existing.setDate(updatedExpense.getDate());

        return repository.save(existing);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }
}
