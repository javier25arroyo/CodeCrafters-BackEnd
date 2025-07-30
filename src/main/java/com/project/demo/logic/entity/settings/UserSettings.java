package com.project.demo.logic.entity.settings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa la configuración de usuario en el sistema.
 * Esta entidad mapea la tabla 'user_settings' en la base de datos.
 */
@Entity
@Table(name = "user_settings")
public class UserSettings {
    /**
     * Identificador único de la configuración de usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tema de la interfaz de usuario (ej. "claro", "oscuro").
     */
    @Column(length = 20)
    private String theme;

    /**
     * Idioma preferido del usuario (ej. "es", "en").
     */
    @Column(length = 20)
    private String language;

    /**
     * Indica si el texto grande está habilitado para accesibilidad.
     */
    @Column(name = "large_text")
    private Boolean largeText;

    /**
     * Indica si el modo de alto contraste está habilitado para accesibilidad.
     */
    @Column(name = "high_contrast")
    private Boolean highContrast;

    /**
     * Volumen preferido para los sonidos de la aplicación.
     */
    private Integer volume;

    /**
     * El usuario al que pertenece esta configuración.
     */
    @JsonBackReference("user-settings")
    @OneToOne(mappedBy = "settings")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private LevelEnum level;

    /**
     * Constructor por defecto.
     */
    public UserSettings() {
    }

    /**
     * Constructor para crear una instancia de UserSettings con todos los parámetros.
     *
     * @param id           Identificador único.
     * @param theme        Tema de la interfaz de usuario.
     * @param language     Idioma preferido.
     * @param largeText    Indicador de texto grande.
     * @param highContrast Indicador de alto contraste.
     * @param volume       Volumen preferido.
     * @param user         Usuario asociado a esta configuración.
     * @param level        Nivel de dificultad preferido.
     */
    public UserSettings(Integer id, String theme, String language, Boolean largeText, Boolean highContrast, Integer volume, User user, LevelEnum level) {
        this.id = id;
        this.theme = theme;
        this.language = language;
        this.largeText = largeText;
        this.highContrast = highContrast;
        this.volume = volume;
        this.user = user;
        this.level = level;
    }

    /**
     * Obtiene el ID de la configuración de usuario.
     *
     * @return El ID de la configuración de usuario.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la configuración de usuario.
     *
     * @param id El nuevo ID de la configuración de usuario.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el tema de la interfaz de usuario.
     *
     * @return El tema de la interfaz de usuario.
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Establece el tema de la interfaz de usuario.
     *
     * @param theme El nuevo tema de la interfaz de usuario.
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Obtiene el idioma preferido del usuario.
     *
     * @return El idioma preferido del usuario.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Establece el idioma preferido del usuario.
     *
     * @param language El nuevo idioma preferido del usuario.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Indica si el texto grande está habilitado para accesibilidad.
     *
     * @return True si el texto grande está habilitado, false en caso contrario.
     */
    public Boolean getLargeText() {
        return largeText;
    }

    /**
     * Establece si el texto grande está habilitado para accesibilidad.
     *
     * @param largeText El nuevo estado de texto grande.
     */
    public void setLargeText(Boolean largeText) {
        this.largeText = largeText;
    }

    /**
     * Indica si el modo de alto contraste está habilitado para accesibilidad.
     *
     * @return True si el modo de alto contraste está habilitado, false en caso contrario.
     */
    public Boolean getHighContrast() {
        return highContrast;
    }

    /**
     * Establece si el modo de alto contraste está habilitado para accesibilidad.
     *
     * @param highContrast El nuevo estado de alto contraste.
     */
    public void setHighContrast(Boolean highContrast) {
        this.highContrast = highContrast;
    }

    /**
     * Obtiene el volumen preferido para los sonidos de la aplicación.
     *
     * @return El volumen preferido.
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * Establece el volumen preferido para los sonidos de la aplicación.
     *
     * @param volume El nuevo volumen preferido.
     */
    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    /**
     * Obtiene el usuario al que pertenece esta configuración.
     *
     * @return El usuario al que pertenece esta configuración.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario al que pertenece esta configuración.
     *
     * @param user El nuevo usuario al que pertenece esta configuración.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el nivel de dificultad preferido.
     *
     * @return El nivel de dificultad preferido.
     */
    public LevelEnum getLevel() {
        return level;
    }

    /**
     * Establece el nivel de dificultad preferido.
     *
     * @param level El nuevo nivel de dificultad preferido.
     */
    public void setLevel(LevelEnum level) {
        this.level = level;
    }
}
