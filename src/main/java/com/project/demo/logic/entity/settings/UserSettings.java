package com.project.demo.logic.entity.settings;

import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonBackReference("user-settings")
    @OneToOne(mappedBy = "settings")
    private User user;

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