package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.LoanPaymentRequest;
import com.yaswanth.budgetapp.dto.LoanRequest;
import com.yaswanth.budgetapp.dto.LoanResponse;
import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.Loan;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.LoanRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository repository;
    private final UserRepository userRepository;

    public LoanServiceImpl(LoanRepository repository,
                           UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public LoanResponse createLoan(LoanRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Loan loan = Loan.builder()
                .totalAmount(request.totalAmount())
                .remainingAmount(request.totalAmount())
                .interestRate(request.interestRate())
                .user(user)
                .build();

        return mapToResponse(repository.save(loan));
    }

    @Override
    public List<LoanResponse> getLoansByUserEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return repository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public LoanResponse repay(Long loanId,
                              LoanPaymentRequest request,
                              String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 🔐 SECURE QUERY (prevents accessing other users' loans)
        Loan loan = repository.findByIdAndUser(loanId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));

        if (request.amount() > loan.getRemainingAmount()) {
            throw new BusinessException("Repayment exceeds remaining loan amount");
        }

        double newRemaining = loan.getRemainingAmount() - request.amount();

        loan.setRemainingAmount(newRemaining);

        return mapToResponse(repository.save(loan));
    }

    private LoanResponse mapToResponse(Loan loan) {
        return new LoanResponse(
                loan.getId(),
                loan.getTotalAmount(),
                loan.getRemainingAmount(),
                loan.getInterestRate(),
                loan.getUser().getId()
        );
    }
}