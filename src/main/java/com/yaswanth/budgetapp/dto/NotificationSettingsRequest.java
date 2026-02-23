package com.yaswanth.budgetapp.dto;

import jakarta.validation.constraints.NotNull;

public record NotificationSettingsRequest(

        @NotNull Boolean emailNotifications,
        @NotNull Boolean budgetAlerts,
        @NotNull Boolean goalReminders
) {}