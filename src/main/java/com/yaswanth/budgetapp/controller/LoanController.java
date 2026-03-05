package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.LoanPaymentRequest;
import com.yaswanth.budgetapp.dto.LoanRequest;
import com.yaswanth.budgetapp.dto.LoanResponse;
import com.yaswanth.budgetapp.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(
            @Valid @RequestBody LoanRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.createLoan(request, email));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getLoans() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.getLoansByUserEmail(email));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        service.deleteLoan(id, email);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/repay")
    public ResponseEntity<LoanResponse> repayLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanPaymentRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.repay(id, request, email));
    }
}