package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.ExpenseRequest;
import com.yaswanth.budgetapp.dto.ExpenseResponse;
import com.yaswanth.budgetapp.model.Expense;
import com.yaswanth.budgetapp.service.ExpenseService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ExpenseResponse addExpense(@Valid @RequestBody ExpenseRequest request) {
        return service.addExpense(request);
    }

    @GetMapping("/{id}")
    public ExpenseResponse getExpenseById(@PathVariable Long id) {
        return service.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public ExpenseResponse updateExpense(@PathVariable Long id,
                                         @Valid @RequestBody ExpenseRequest request) {
        return service.updateExpense(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {
        service.deleteExpense(id);
        return "Expense deleted successfully";
    }

    @GetMapping
    public Page<ExpenseResponse> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return service.getAllExpenses(page, size, sortBy, direction);
    }

}


