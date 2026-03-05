package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Loan;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    Optional<Loan> findByIdAndUser(Long id, User user);

    @Query("SELECT SUM(l.principalAmount + (l.principalAmount * l.interestRate / 100) - l.paidAmount) " +
            "FROM Loan l WHERE l.user = :user")
    Double sumRemainingAmountByUser(@Param("user") User user);
}