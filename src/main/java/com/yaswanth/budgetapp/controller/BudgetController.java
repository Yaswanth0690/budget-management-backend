package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.BudgetRequest;
import com.yaswanth.budgetapp.dto.BudgetResponse;
import com.yaswanth.budgetapp.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BudgetResponse> createOrUpdateBudget(
            @Valid @RequestBody BudgetRequest request) {

        return ResponseEntity.ok(service.setBudget(request));
    }

    @GetMapping
    public ResponseEntity<BudgetResponse> getBudgetByUserAndMonth(
            @RequestParam Long userId,
            @RequestParam String month) {

        return ResponseEntity.ok(
                service.getBudgetByUserAndMonth(userId, month)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBudget(
            @PathVariable Long id) {

        service.deleteBudget(id);
        return ResponseEntity.noContent().build();
    }
}
