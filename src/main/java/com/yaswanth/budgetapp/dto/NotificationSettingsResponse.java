package com.yaswanth.budgetapp.dto;

public record NotificationSettingsResponse(
        Long id,
        Boolean emailNotifications,
        Boolean budgetAlerts,
        Boolean goalReminders
) {}