package com.project.demo.service;

import com.project.demo.logic.entity.settings.UserSettings;
import com.project.demo.logic.entity.settings.UserSettingsRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.exceptions.UserNotFoundException;
import com.project.demo.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LoggingService loggingService;

    @Override
    public UserSettings updateUserSettings(Long userId, UserSettings settings) {
        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId, "updateUserSettings"));
            
            UserSettings userSettings = user.getSettings();
            if (userSettings == null) {
                userSettings = new UserSettings();
                userSettings.setUser(user);
                user.setSettings(userSettings);
            }
            
            updateSettingsFields(userSettings, settings);
            UserSettings savedSettings = userSettingsRepository.save(userSettings);
            
            loggingService.logBusinessOperation("UPDATE_USER_SETTINGS", 
                "User settings updated successfully", 
                Map.of("userId", userId, "settingsId", savedSettings.getId()));
                
            return savedSettings;
            
        } catch (Exception e) {
            loggingService.logError(e, "UserSettingsServiceImpl", "updateUserSettings", "UPDATE_USER_SETTINGS");
            throw e;
        }
    }

    @Override
    public UserSettings getUserSettings(Long userId) {
        try {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId, "getUserSettings"));
            return user.getSettings();
            
        } catch (Exception e) {
            loggingService.logError(e, "UserSettingsServiceImpl", "getUserSettings", "GET_USER_SETTINGS");
            throw e;
        }
    }
    
    private void updateSettingsFields(UserSettings target, UserSettings source) {
        target.setTheme(source.getTheme());
        target.setLanguage(source.getLanguage());
        target.setLargeText(source.getLargeText());
        target.setHighContrast(source.getHighContrast());
        target.setVolume(source.getVolume());
        target.setLevel(source.getLevel());
    }
}
