package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Loan;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    Optional<Loan> findByIdAndUser(Long id, User user);
}