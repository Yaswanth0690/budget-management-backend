package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.dto.CategoryExpenseSummary;
import com.yaswanth.budgetapp.model.Expense;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Page<Expense> findByUser(User user, Pageable pageable);

    @Query("""
           SELECT new com.yaswanth.budgetapp.dto.CategoryExpenseSummary(
               e.category.name,
               SUM(e.amount)
           )
           FROM Expense e
           WHERE e.user = :user
           GROUP BY e.category.name
           """)
    List<CategoryExpenseSummary> getCategoryWiseSummary(@Param("user") User user);

    @Query("""
           SELECT new com.yaswanth.budgetapp.dto.CategoryExpenseSummary(
               e.category.name,
               SUM(e.amount)
           )
           FROM Expense e
           WHERE e.user = :user 
           AND FUNCTION('MONTH', e.date) = :month 
           AND FUNCTION('YEAR', e.date) = :year
           GROUP BY e.category.name
           """)
    List<CategoryExpenseSummary> getCategoryWiseSummaryByMonth(
            @Param("user") User user,
            @Param("month") int month,
            @Param("year") int year);

    @Query("""
           SELECT SUM(e.amount) 
           FROM Expense e 
           WHERE e.user = :user 
           AND FUNCTION('MONTH', e.date) = :month 
           AND FUNCTION('YEAR', e.date) = :year
           """)
    Double sumTotalAmountByMonth(
            @Param("user") User user,
            @Param("month") int month,
            @Param("year") int year);
}