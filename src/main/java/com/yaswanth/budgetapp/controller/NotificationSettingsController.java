package com.yaswanth.budgetapp.controller;

import com.yaswanth.budgetapp.dto.NotificationSettingsRequest;
import com.yaswanth.budgetapp.dto.NotificationSettingsResponse;
import com.yaswanth.budgetapp.service.NotificationSettingsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationSettingsController {

    private final NotificationSettingsService service;

    public NotificationSettingsController(
            NotificationSettingsService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<NotificationSettingsResponse> update(
            @Valid @RequestBody NotificationSettingsRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                service.updateSettings(request, email)
        );
    }

    @GetMapping
    public ResponseEntity<NotificationSettingsResponse> get() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                service.getSettings(email)
        );
    }
}