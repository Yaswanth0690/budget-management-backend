package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.SavingsGoal;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {

    List<SavingsGoal> findByUser(User user);

    Optional<SavingsGoal> findByGoalNameAndUser(String goalName, User user);
}