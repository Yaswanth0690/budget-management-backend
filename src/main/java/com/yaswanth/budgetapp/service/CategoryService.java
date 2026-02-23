package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.model.Category;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.CategoryRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category createCategory(String name, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found"));

        if (categoryRepository.existsByNameAndUser(name, user)) {
            throw new BusinessException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public List<Category> getCategories(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found"));

        return categoryRepository.findByUser(user);
    }
}