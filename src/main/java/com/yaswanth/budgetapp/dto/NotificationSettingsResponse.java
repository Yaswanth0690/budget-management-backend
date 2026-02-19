package com.yaswanth.budgetapp.dto;

public record NotificationSettingsResponse(
        Long id,
        Boolean budgetAlertEnabled,
        Boolean loanReminderEnabled,
        Long userId
) {}
