package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa un reporte de juego, que resume el resultado de una sesión de juego. Esta entidad
 * mapea la tabla 'game_reports' en la base de datos.
 */
@Entity
@Table(name = "game_reports")
public class GameReport {
    /** Identificador único del reporte de juego. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** El usuario que generó este reporte de juego. */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** El juego al que corresponde este reporte. */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /** Duración del juego en segundos. */
    private Integer duration;

    /** Puntuación obtenida en el juego. */
    private Integer score;

    /** Resultado del juego (ej. "victoria", "derrota", "empate"). */
    @Column(length = 50)
    private String result;

    /** Logros obtenidos durante el juego, almacenados como texto. */
    @Column(columnDefinition = "TEXT")
    private String achievements;

    /** Fecha y hora en que se generó el reporte. */
    private LocalDateTime date;

    /** Constructor por defecto. */
    public GameReport() {}

    /**
     * Constructor para crear una instancia de GameReport con todos los parámetros.
     *
     * @param id Identificador único.
     * @param user Usuario que generó el reporte.
     * @param game Juego asociado.
     * @param duration Duración del juego.
     * @param score Puntuación obtenida.
     * @param result Resultado del juego.
     * @param achievements Logros obtenidos.
     * @param date Fecha del reporte.
     */
    public GameReport(
            Integer id,
            User user,
            Game game,
            Integer duration,
            Integer score,
            String result,
            String achievements,
            LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.duration = duration;
        this.score = score;
        this.result = result;
        this.achievements = achievements;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
