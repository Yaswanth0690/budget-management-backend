package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.SavingsGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;
import com.yaswanth.budgetapp.dto.AddAmountRequest;
import com.yaswanth.budgetapp.service.SavingsGoalService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/savings-goals")
public class SavingsGoalController {

    private final SavingsGoalService savingsGoalService;

    public SavingsGoalController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    // ✅ Create new savings goal
    @PostMapping
    public ResponseEntity<SavingsGoalResponse> createGoal(
            @Valid @RequestBody SavingsGoalRequest request) {

        SavingsGoalResponse response =
                savingsGoalService.createGoal(request);

        return ResponseEntity.ok(response);
    }

    // ✅ Get all goals for a user
    @GetMapping
    public ResponseEntity<List<SavingsGoalResponse>> getGoalsByUser(
            @RequestParam Long userId) {

        List<SavingsGoalResponse> goals =
                savingsGoalService.getGoalsByUser(userId);

        return ResponseEntity.ok(goals);
    }

    // ✅ Contribute amount to goal
    @PutMapping("/{goalId}/contribute")
    public ResponseEntity<SavingsGoalResponse> contributeToGoal(
            @PathVariable Long goalId,
            @Valid @RequestBody AddAmountRequest request) {

        SavingsGoalResponse response =
                savingsGoalService.contribute(goalId, request);

        return ResponseEntity.ok(response);
    }
}
