package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa una sesión de juego de un usuario.
 * Esta entidad mapea la tabla 'game_sessions' en la base de datos.
 */
@Entity
@Table(name = "game_sessions")
public class GameSession {
    /**
     * Identificador único de la sesión de juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario que participa en esta sesión de juego.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El juego al que corresponde esta sesión.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * El estado actual de la sesión de juego (ej. "iniciada", "finalizada", "pausada").
     */
    @Column(length = 20)
    private String status;

    /**
     * Duración total de la sesión de juego en segundos.
     */
    @Column(name = "total_duration")
    private Integer totalDuration;

    /**
     * Puntuación parcial obtenida durante la sesión.
     */
    @Column(name = "partial_score")
    private Integer partialScore;

    /**
     * Fecha y hora de inicio de la sesión de juego.
     */
    private LocalDateTime start;

    /**
     * Fecha y hora de finalización de la sesión de juego.
     */
    private LocalDateTime end;

    /**
     * Constructor por defecto.
     */
    public GameSession() {
    }

    /**
     * Constructor para crear una instancia de GameSession con todos los parámetros.
     *
     * @param id            Identificador único.
     * @param user          Usuario de la sesión.
     * @param game          Juego de la sesión.
     * @param status        Estado de la sesión.
     * @param totalDuration Duración total.
     * @param partialScore  Puntuación parcial.
     * @param start         Fecha y hora de inicio.
     * @param end           Fecha y hora de finalización.
     */
    public GameSession(Integer id, User user, Game game, String status, Integer totalDuration, Integer partialScore, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.status = status;
        this.totalDuration = totalDuration;
        this.partialScore = partialScore;
        this.start = start;
        this.end = end;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Integer partialScore) {
        this.partialScore = partialScore;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}

