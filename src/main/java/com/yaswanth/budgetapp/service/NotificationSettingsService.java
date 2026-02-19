package com.yaswanth.budgetapp.service;

import com.yaswanth.budgetapp.dto.NotificationSettingsRequest;
import com.yaswanth.budgetapp.dto.NotificationSettingsResponse;

public interface NotificationSettingsService {

    NotificationSettingsResponse saveSettings(NotificationSettingsRequest request);

    NotificationSettingsResponse getByUserId(Long userId);
}
