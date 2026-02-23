package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.AddAmountRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;
import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.SavingsGoal;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.SavingsGoalRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGoalServiceImpl implements SavingsGoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final UserRepository userRepository;

    public SavingsGoalServiceImpl(SavingsGoalRepository savingsGoalRepository,
                                  UserRepository userRepository) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SavingsGoalResponse createGoal(SavingsGoalRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Prevent duplicate goal names per user
        savingsGoalRepository.findByGoalNameAndUser(request.goalName(), user)
                .ifPresent(g -> {
                    throw new BusinessException("Goal with this name already exists");
                });

        SavingsGoal goal = SavingsGoal.builder()
                .goalName(request.goalName())
                .targetAmount(request.targetAmount())
                .savedAmount(0.0)
                .user(user)
                .build();

        return mapToResponse(savingsGoalRepository.save(goal));
    }

    @Override
    public List<SavingsGoalResponse> getGoalsByUserEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return savingsGoalRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SavingsGoalResponse contribute(Long goalId,
                                          AddAmountRequest request,
                                          String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavingsGoal goal = savingsGoalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Savings goal not found"));

        // Ownership validation
        if (!goal.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Unauthorized access");
        }

        double newAmount = goal.getSavedAmount() + request.amount();

        if (newAmount > goal.getTargetAmount()) {
            throw new BusinessException("Contribution exceeds target amount");
        }

        goal.setSavedAmount(newAmount);

        return mapToResponse(savingsGoalRepository.save(goal));
    }

    @Override
    public void deleteGoal(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavingsGoal goal = savingsGoalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Savings goal not found"));

        if (!goal.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Unauthorized access");
        }

        savingsGoalRepository.delete(goal);
    }

    private SavingsGoalResponse mapToResponse(SavingsGoal goal) {
        return new SavingsGoalResponse(
                goal.getId(),
                goal.getGoalName(),
                goal.getTargetAmount(),
                goal.getSavedAmount(),
                goal.getUser().getId()
        );
    }
}