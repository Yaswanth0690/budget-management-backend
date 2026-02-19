package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.LoanPaymentRequest;
import com.yaswanth.budgetapp.dto.LoanRequest;
import com.yaswanth.budgetapp.dto.LoanResponse;
import com.yaswanth.budgetapp.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

        return ResponseEntity.ok(service.createLoan(request));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getLoansByUser(
            @RequestParam Long userId) {

        return ResponseEntity.ok(service.getLoansByUser(userId));
    }

    @PutMapping("/{id}/repay")
    public ResponseEntity<LoanResponse> repayLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanPaymentRequest request) {

        return ResponseEntity.ok(service.repay(id, request));
    }
}
