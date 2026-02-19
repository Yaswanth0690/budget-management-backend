package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.NotificationSettingsRequest;
import com.yaswanth.budgetapp.dto.NotificationSettingsResponse;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.NotificationSettings;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.NotificationSettingsRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationSettingsServiceImpl implements NotificationSettingsService {

    private final NotificationSettingsRepository repository;
    private final UserRepository userRepository;

    public NotificationSettingsServiceImpl(NotificationSettingsRepository repository,
                                           UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationSettingsResponse saveSettings(NotificationSettingsRequest request) {

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        NotificationSettings settings = NotificationSettings.builder()
                .budgetAlertEnabled(request.budgetAlertEnabled())
                .loanReminderEnabled(request.loanReminderEnabled())
                .user(user)
                .build();

        NotificationSettings saved = repository.save(settings);

        return mapToResponse(saved);
    }

    @Override
    public NotificationSettingsResponse getByUserId(Long userId) {

        NotificationSettings settings = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Settings not found"));

        return mapToResponse(settings);
    }

    private NotificationSettingsResponse mapToResponse(NotificationSettings settings) {
        return new NotificationSettingsResponse(
                settings.getId(),
                settings.getBudgetAlertEnabled(),
                settings.getLoanReminderEnabled(),
                settings.getUser().getId()
        );
    }
}
