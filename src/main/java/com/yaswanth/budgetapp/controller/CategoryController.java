package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.CategoryResponse;
import com.yaswanth.budgetapp.service.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponse> getAllCategories(Authentication authentication) {
        String email = authentication.getName();

        return categoryService.getCategories(email)
                .stream()
                .map(cat -> new CategoryResponse(
                        cat.getId(),
                        cat.getName()
                ))
                .toList();
    }
}