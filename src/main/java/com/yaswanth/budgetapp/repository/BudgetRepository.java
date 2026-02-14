package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserIdAndMonth(Long userId, String month);
}

