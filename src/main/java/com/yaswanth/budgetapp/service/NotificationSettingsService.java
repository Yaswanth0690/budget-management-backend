package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.NotificationSettingsRequest;
import com.yaswanth.budgetapp.dto.NotificationSettingsResponse;

public interface NotificationSettingsService {

    NotificationSettingsResponse updateSettings(
            NotificationSettingsRequest request,
            String email
    );

    NotificationSettingsResponse getSettings(String email);
}