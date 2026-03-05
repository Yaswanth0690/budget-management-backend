package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.model.Category;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.CategoryRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // 🔥 Define your strict default categories here
    private static final List<String> DEFAULT_CATEGORIES = Arrays.asList("Food", "Bills", "Travel", "Others");

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Helper method to generate the default categories for a user
    private void createDefaultCategoriesForUser(User user) {
        for (String categoryName : DEFAULT_CATEGORIES) {
            if (!categoryRepository.existsByNameAndUser(categoryName, user)) {
                Category category = new Category();
                category.setName(categoryName);
                category.setUser(user);
                categoryRepository.save(category);
            }
        }
    }

    @Override
    public List<Category> getCategories(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User not found"));

        List<Category> categories = categoryRepository.findByUser(user);

        // 🔥 FAILSAFE: If the user has 0 categories, automatically create the defaults and fetch again
        if (categories.isEmpty()) {
            createDefaultCategoriesForUser(user);
            categories = categoryRepository.findByUser(user);
        }

        return categories;
    }
}