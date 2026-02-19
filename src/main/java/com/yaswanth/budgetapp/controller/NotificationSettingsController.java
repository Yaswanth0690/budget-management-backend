package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.NotificationSettingsRequest;
import com.yaswanth.budgetapp.dto.NotificationSettingsResponse;
import com.yaswanth.budgetapp.service.NotificationSettingsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-settings")
public class NotificationSettingsController {

    private final NotificationSettingsService service;

    public NotificationSettingsController(NotificationSettingsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<NotificationSettingsResponse> saveSettings(
            @Valid @RequestBody NotificationSettingsRequest request) {

        return ResponseEntity.ok(service.saveSettings(request));
    }

    @GetMapping
    public ResponseEntity<NotificationSettingsResponse> getSettingsByUser(
            @RequestParam Long userId) {

        return ResponseEntity.ok(service.getByUserId(userId));
    }
}
