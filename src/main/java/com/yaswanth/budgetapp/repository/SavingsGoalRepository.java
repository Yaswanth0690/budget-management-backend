package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.SavingsGoal;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {

    List<SavingsGoal> findByUser(User user);

    Optional<SavingsGoal> findByGoalNameAndUser(String goalName, User user);

    long countByUser(User user);

    @Query("SELECT SUM(g.targetAmount) FROM SavingsGoal g WHERE g.user = :user")
    Double sumTargetAmountByUser(@Param("user") User user);

    @Query("SELECT SUM(g.savedAmount) FROM SavingsGoal g WHERE g.user = :user")
    Double sumSavedAmountByUser(@Param("user") User user);
}