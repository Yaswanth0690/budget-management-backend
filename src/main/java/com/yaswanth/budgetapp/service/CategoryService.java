package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.model.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getCategories(String email);
}