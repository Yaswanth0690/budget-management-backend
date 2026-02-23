package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.NotificationSettingsRequest;
import com.yaswanth.budgetapp.dto.NotificationSettingsResponse;
import com.yaswanth.budgetapp.exception.ResourceNotFoundException;
import com.yaswanth.budgetapp.model.NotificationSettings;
import com.yaswanth.budgetapp.model.User;
import com.yaswanth.budgetapp.repository.NotificationSettingsRepository;
import com.yaswanth.budgetapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationSettingsServiceImpl
        implements NotificationSettingsService {

    private final NotificationSettingsRepository repository;
    private final UserRepository userRepository;

    public NotificationSettingsServiceImpl(
            NotificationSettingsRepository repository,
            UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationSettingsResponse updateSettings(
            NotificationSettingsRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        NotificationSettings settings = repository
                .findByUserId(user.getId())
                .orElseGet(() ->
                        NotificationSettings.builder()
                                .user(user)
                                .emailNotifications(true)
                                .budgetAlerts(true)
                                .goalReminders(true)
                                .build()
                );

        settings.setEmailNotifications(request.emailNotifications());
        settings.setBudgetAlerts(request.budgetAlerts());
        settings.setGoalReminders(request.goalReminders());

        return mapToResponse(repository.save(settings));
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationSettingsResponse getSettings(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        NotificationSettings settings = repository
                .findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification settings not found"
                        ));

        return mapToResponse(settings);
    }

    private NotificationSettingsResponse mapToResponse(
            NotificationSettings settings) {

        return new NotificationSettingsResponse(
                settings.getId(),
                settings.isEmailNotifications(),
                settings.isBudgetAlerts(),
                settings.isGoalReminders()
        );
    }
}