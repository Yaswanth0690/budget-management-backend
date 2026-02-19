package com.yaswanth.budgetapp.dto;

public record NotificationSettingsRequest(

        Long userId,
        Boolean budgetAlertEnabled,
        Boolean loanReminderEnabled
) {}
