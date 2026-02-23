package com.yaswanth.budgetapp.repository;

import com.yaswanth.budgetapp.model.Category;
import com.yaswanth.budgetapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNameAndUser(String name, User user);

    Optional<Category> findByNameAndUser(String name, User user);

    List<Category> findByUser(User user);
}