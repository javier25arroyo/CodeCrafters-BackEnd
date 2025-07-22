package com.project.demo.rest.settings;

import com.project.demo.logic.entity.settings.UserSettings;
import com.project.demo.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public ResponseEntity<UserSettings> updateUserSettings(@PathVariable Long userId, @RequestBody UserSettings settings) {
        return ResponseEntity.ok(userSettingsService.updateUserSettings(userId, settings));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public ResponseEntity<UserSettings> getUserSettings(@PathVariable Long userId) {
        return ResponseEntity.ok(userSettingsService.getUserSettings(userId));
    }
}
