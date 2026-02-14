package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.model.Expense;
import java.util.List;

public interface ExpenseService {

    Expense addExpense(Expense expense);

    Expense getExpenseById(Long id);

    void deleteExpense(Long id);

    Expense updateExpense(Long id, Expense expense);

    List<Expense> getAllExpenses();
}
