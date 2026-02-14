package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class ExpenseRequest {

    @NotNull
    @Positive
    private double amount;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long categoryId;

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
