package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa un reporte de juego, que resume el resultado de una sesión de juego.
 * Esta entidad mapea la tabla 'game_reports' en la base de datos.
 */
@Entity
@Table(name = "game_reports")
public class GameReport {
    /**
     * Identificador único del reporte de juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario que generó este reporte de juego.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El juego al que corresponde este reporte.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * Duración del juego en segundos.
     */
    private Integer duration;

    /**
     * Puntuación obtenida en el juego.
     */
    private Integer score;

    /**
     * Resultado del juego (ej. "victoria", "derrota", "empate").
     */
    @Column(length = 50)
    private String result;

    /**
     * Logros obtenidos durante el juego, almacenados como texto.
     */
    @Column(columnDefinition = "TEXT")
    private String achievements;

    /**
     * Fecha y hora en que se generó el reporte.
     */
    private LocalDateTime date;

    /**
     * Constructor por defecto.
     */
    public GameReport() {
    }

    /**
     * Constructor para crear una instancia de GameReport con todos los parámetros.
     *
     * @param id           Identificador único.
     * @param user         Usuario que generó el reporte.
     * @param game         Juego asociado.
     * @param duration     Duración del juego.
     * @param score        Puntuación obtenida.
     * @param result       Resultado del juego.
     * @param achievements Logros obtenidos.
     * @param date         Fecha del reporte.
     */
    public GameReport(Integer id, User user, Game game, Integer duration, Integer score, String result, String achievements, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.duration = duration;
        this.score = score;
        this.result = result;
        this.achievements = achievements;
        this.date = date;
    }

    /**
     * Obtiene el ID del reporte de juego.
     *
     * @return El ID del reporte de juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del reporte de juego.
     *
     * @param id El nuevo ID del reporte de juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que generó este reporte de juego.
     *
     * @return El usuario que generó este reporte de juego.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que generó este reporte de juego.
     *
     * @param user El nuevo usuario que generó este reporte de juego.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el juego al que corresponde este reporte.
     *
     * @return El juego al que corresponde este reporte.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece el juego al que corresponde este reporte.
     *
     * @param game El nuevo juego al que corresponde este reporte.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Obtiene la duración del juego en segundos.
     *
     * @return La duración del juego en segundos.
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Establece la duración del juego en segundos.
     *
     * @param duration La nueva duración del juego en segundos.
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Obtiene la puntuación obtenida en el juego.
     *
     * @return La puntuación obtenida en el juego.
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Establece la puntuación obtenida en el juego.
     *
     * @param score La nueva puntuación obtenida en el juego.
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Obtiene el resultado del juego.
     *
     * @return El resultado del juego.
     */
    public String getResult() {
        return result;
    }

    /**
     * Establece el resultado del juego.
     *
     * @param result El nuevo resultado del juego.
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Obtiene los logros obtenidos durante el juego.
     *
     * @return Los logros obtenidos durante el juego.
     */
    public String getAchievements() {
        return achievements;
    }

    /**
     * Establece los logros obtenidos durante el juego.
     *
     * @param achievements Los nuevos logros obtenidos durante el juego.
     */
    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    /**
     * Obtiene la fecha y hora en que se generó el reporte.
     *
     * @return La fecha y hora en que se generó el reporte.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha y hora en que se generó el reporte.
     *
     * @param date La nueva fecha y hora en que se generó el reporte.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

