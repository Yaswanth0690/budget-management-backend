package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.AddAmountRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;
import com.yaswanth.budgetapp.service.SavingsGoalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    private final SavingsGoalService service;

    public SavingsGoalController(SavingsGoalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SavingsGoalResponse> createGoal(
            @Valid @RequestBody SavingsGoalRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.createGoal(request, email));
    }

    @GetMapping
    public ResponseEntity<List<SavingsGoalResponse>> getGoals() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.getGoalsByUserEmail(email));
    }

    @PostMapping("/{id}/contribute")
    public ResponseEntity<SavingsGoalResponse> contribute(
            @PathVariable Long id,
            @Valid @RequestBody AddAmountRequest request) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(service.contribute(id, request, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        service.deleteGoal(id, email);
        return ResponseEntity.noContent().build();
    }
}