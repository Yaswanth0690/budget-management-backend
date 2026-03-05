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
                .loanName(request.loanName())
                .principalAmount(request.principalAmount())
                .interestRate(request.interestRate())
                .paidAmount(0.0)
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

        Loan loan = repository.findByIdAndUser(loanId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));

        double totalAmount = calculateTotal(loan);
        double remainingAmount = totalAmount - loan.getPaidAmount();

        if (request.amount() > remainingAmount) {
            throw new BusinessException("Repayment exceeds remaining loan amount");
        }

        loan.setPaidAmount(loan.getPaidAmount() + request.amount());

        return mapToResponse(repository.save(loan));
    }

    private LoanResponse mapToResponse(Loan loan) {

        double totalAmount = calculateTotal(loan);
        double remainingAmount = totalAmount - loan.getPaidAmount();

        return new LoanResponse(
                loan.getId(),
                loan.getLoanName(),
                loan.getPrincipalAmount(),
                loan.getInterestRate(),
                totalAmount,
                loan.getPaidAmount(),
                remainingAmount,
                loan.getUser().getId()
        );
    }

    @Override
    public void deleteLoan(Long loanId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Loan loan = repository.findByIdAndUser(loanId, user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));

        repository.delete(loan);
    }

    private double calculateTotal(Loan loan) {
        return loan.getPrincipalAmount()
                + (loan.getPrincipalAmount() * loan.getInterestRate() / 100);
    }
}