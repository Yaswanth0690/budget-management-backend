package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.UserRequest;
import com.yaswanth.budgetapp.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getByEmail(String email);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);
}
