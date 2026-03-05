package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.CategoryExpenseSummary;
import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import com.yaswanth.budgetapp.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(
            @Valid @RequestBody ExpenseRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.createExpense(request, email));
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseResponse>> getExpenses(Pageable pageable) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                service.getExpensesByUserEmail(email, pageable)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.updateExpense(id, request, email));
    }

    @GetMapping("/summary/category")
    public ResponseEntity<List<CategoryExpenseSummary>> getCategorySummary() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                service.getCategoryWiseSummary(email)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        service.deleteExpense(id, email);

        return ResponseEntity.noContent().build();
    }
}