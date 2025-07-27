package com.project.demo.service;

import com.project.demo.logic.entity.settings.UserSettings;

public interface UserSettingsService {
    UserSettings updateUserSettings(Long userId, UserSettings settings);

    UserSettings getUserSettings(Long userId);
}
