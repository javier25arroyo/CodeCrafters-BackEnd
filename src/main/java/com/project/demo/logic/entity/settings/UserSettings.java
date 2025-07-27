package com.project.demo.logic.entity.settings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa la configuración de usuario en el sistema. Esta entidad mapea la tabla 'user_settings'
 * en la base de datos.
 */
@Entity
@Table(name = "user_settings")
public class UserSettings {
    /** Identificador único de la configuración de usuario. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Tema de la interfaz de usuario (ej. "claro", "oscuro"). */
    @Column(length = 20)
    private String theme;

    /** Idioma preferido del usuario (ej. "es", "en"). */
    @Column(length = 20)
    private String language;

    /** Indica si el texto grande está habilitado para accesibilidad. */
    @Column(name = "large_text")
    private Boolean largeText;

    /** Indica si el modo de alto contraste está habilitado para accesibilidad. */
    @Column(name = "high_contrast")
    private Boolean highContrast;

    /** Volumen preferido para los sonidos de la aplicación. */
    private Integer volume;

    /** El usuario al que pertenece esta configuración. */
    @JsonBackReference("user-settings")
    @OneToOne(mappedBy = "settings")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private LevelEnum level;

    /** Constructor por defecto. */
    public UserSettings() {}

    /**
     * Constructor para crear una instancia de UserSettings con todos los parámetros.
     *
     * @param id Identificador único.
     * @param theme Tema de la interfaz de usuario.
     * @param language Idioma preferido.
     * @param largeText Indicador de texto grande.
     * @param highContrast Indicador de alto contraste.
     * @param volume Volumen preferido.
     * @param user Usuario asociado a esta configuración.
     * @param level Nivel de dificultad preferido.
     */
    public UserSettings(
            Integer id,
            String theme,
            String language,
            Boolean largeText,
            Boolean highContrast,
            Integer volume,
            User user,
            LevelEnum level) {
        this.id = id;
        this.theme = theme;
        this.language = language;
        this.largeText = largeText;
        this.highContrast = highContrast;
        this.volume = volume;
        this.user = user;
        this.level = level;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }
}
