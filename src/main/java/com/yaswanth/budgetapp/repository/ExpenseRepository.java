package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
