package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
