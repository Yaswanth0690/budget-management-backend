package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserId(Long userId);

    @Query("SELECT SUM(b.amount) FROM Budget b WHERE b.user.id = :userId " +
            "AND b.month = :month AND b.year = :year")
    Double sumAmountByUserIdAndMonth(
            @Param("userId") Long userId,
            @Param("month") int month,
            @Param("year") int year);

    Optional<Budget> findByUserIdAndMonth(Long userId, Integer month);

    Optional<Budget> findByIdAndUserId(Long id, Long userId);

    void deleteByIdAndUserId(Long id, Long userId);
}