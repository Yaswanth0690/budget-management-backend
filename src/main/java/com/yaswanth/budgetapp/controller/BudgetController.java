package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.BudgetRequest;
import com.yaswanth.budgetapp.dto.BudgetResponse;
import com.yaswanth.budgetapp.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> createBudget(
            @Valid @RequestBody BudgetRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.setBudget(request, email));
    }

    @GetMapping
    public ResponseEntity<List<BudgetResponse>> getBudgets() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.getBudgetsByUserEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        service.deleteBudget(id, email);
        return ResponseEntity.noContent().build();
    }
}