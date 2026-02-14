package com.yaswanth.budgetapp.dto;

import java.time.LocalDate;

public class ExpenseResponse {

    private Long id;
    private double amount;
    private String description;
    private LocalDate date;
    private String categoryName;

    public ExpenseResponse(Long id, double amount,
                           String description,
                           LocalDate date,
                           String categoryName) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.categoryName = categoryName;
    }

    public Long getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDate getDate() { return date; }
    public String getCategoryName() { return categoryName; }
}
