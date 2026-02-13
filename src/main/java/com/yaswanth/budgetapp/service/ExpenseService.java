package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.model.Expense;
import java.util.List;

public interface ExpenseService {

    Expense addExpense(Expense expense);

    List<Expense> getAllExpenses();
}
