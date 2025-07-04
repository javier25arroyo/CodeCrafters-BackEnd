package com.project.demo.logic.entity.settings;

import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_settings")
public class UserSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String theme;

    @Column(length = 20)
    private String language;

    @Column(name = "large_text")
    private Boolean largeText;

    @Column(name = "high_contrast")
    private Boolean highContrast;

    private Integer volume;

    

    public UserSettings() {
    }

    public UserSettings(Integer id, String theme, String language, Boolean largeText, Boolean highContrast, Integer volume) {
        this.id = id;
        this.theme = theme;
        this.language = language;
        this.largeText = largeText;
        this.highContrast = highContrast;
        this.volume = volume;
    }

    public UserSettings(String theme, String language, Boolean largeText, Boolean highContrast, Integer volume) {
        this.theme = theme;
        this.language = language;
        this.largeText = largeText;
        this.highContrast = highContrast;
        this.volume = volume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getLargeText() {
        return largeText;
    }

    public void setLargeText(Boolean largeText) {
        this.largeText = largeText;
    }

    public Boolean getHighContrast() {
        return highContrast;
    }

    public void setHighContrast(Boolean highContrast) {
        this.highContrast = highContrast;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

}

/*
UserSettings
CREATE TABLE UserSettings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    theme VARCHAR(20),
    language VARCHAR(20),
    large_text BOOLEAN,
    high_contrast BOOLEAN,
    volume INT
);
*/