package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.AddAmountRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalRequest;
import com.yaswanth.budgetapp.dto.SavingsGoalResponse;
import com.yaswanth.budgetapp.exception.BusinessException;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.SavingsGoal;
import com.yaswanth.budgetapp.repository.SavingsGoalRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGoalServiceImpl implements SavingsGoalService {

    private final SavingsGoalRepository repository;
    private final UserRepository userRepository;

    public SavingsGoalServiceImpl(SavingsGoalRepository repository,
                                  UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public SavingsGoalResponse createGoal(SavingsGoalRequest request) {

        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavingsGoal goal = SavingsGoal.builder()
                .goalName(request.goalName())
                .targetAmount(request.targetAmount())
                .savedAmount(0.0)
                .user(user)
                .build();

        return mapToResponse(repository.save(goal));
    }

    @Override
    public List<SavingsGoalResponse> getGoalsByUser(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SavingsGoalResponse contribute(Long goalId, AddAmountRequest request) {

        SavingsGoal goal = repository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));

        if (goal.getSavedAmount() + request.amount() > goal.getTargetAmount()) {
            throw new BusinessException("Contribution exceeds target amount");
        }

        goal.setSavedAmount(goal.getSavedAmount() + request.amount());

        return mapToResponse(repository.save(goal));
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
