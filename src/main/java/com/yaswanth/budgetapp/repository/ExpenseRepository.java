package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Expense;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUser(User user, Pageable pageable);

}