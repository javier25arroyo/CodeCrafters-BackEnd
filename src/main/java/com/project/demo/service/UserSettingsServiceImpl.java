package com.project.demo.service;

import com.project.demo.logic.entity.settings.UserSettings;
import com.project.demo.logic.entity.settings.UserSettingsRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {

    @Autowired private UserSettingsRepository userSettingsRepository;

    @Autowired private UserRepository userRepository;

    @Override
    public UserSettings updateUserSettings(Long userId, UserSettings settings) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        UserSettings userSettings = user.getSettings();
        if (userSettings == null) {
            userSettings = new UserSettings();
            userSettings.setUser(user);
            user.setSettings(userSettings);
        }
        userSettings.setTheme(settings.getTheme());
        userSettings.setLanguage(settings.getLanguage());
        userSettings.setLargeText(settings.getLargeText());
        userSettings.setHighContrast(settings.getHighContrast());
        userSettings.setVolume(settings.getVolume());
        userSettings.setLevel(settings.getLevel());
        return userSettingsRepository.save(userSettings);
    }

    @Override
    public UserSettings getUserSettings(Long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getSettings();
    }
}
