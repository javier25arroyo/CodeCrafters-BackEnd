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

    /**
     * Obtiene el ID de la sesión de juego.
     *
     * @return El ID de la sesión de juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la sesión de juego.
     *
     * @param id El nuevo ID de la sesión de juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que participa en esta sesión de juego.
     *
     * @return El usuario que participa en esta sesión de juego.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que participa en esta sesión de juego.
     *
     * @param user El nuevo usuario que participa en esta sesión de juego.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el juego al que corresponde esta sesión.
     *
     * @return El juego al que corresponde esta sesión.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece el juego al que corresponde esta sesión.
     *
     * @param game El nuevo juego al que corresponde esta sesión.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Obtiene el estado actual de la sesión de juego.
     *
     * @return El estado actual de la sesión de juego.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado actual de la sesión de juego.
     *
     * @param status El nuevo estado actual de la sesión de juego.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtiene la duración total de la sesión de juego en segundos.
     *
     * @return La duración total de la sesión de juego en segundos.
     */
    public Integer getTotalDuration() {
        return totalDuration;
    }

    /**
     * Establece la duración total de la sesión de juego en segundos.
     *
     * @param totalDuration La nueva duración total de la sesión de juego en segundos.
     */
    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    /**
     * Obtiene la puntuación parcial obtenida durante la sesión.
     *
     * @return La puntuación parcial obtenida durante la sesión.
     */
    public Integer getPartialScore() {
        return partialScore;
    }

    /**
     * Establece la puntuación parcial obtenida durante la sesión.
     *
     * @param partialScore La nueva puntuación parcial obtenida durante la sesión.
     */
    public void setPartialScore(Integer partialScore) {
        this.partialScore = partialScore;
    }

    /**
     * Obtiene la fecha y hora de inicio de la sesión de juego.
     *
     * @return La fecha y hora de inicio de la sesión de juego.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Establece la fecha y hora de inicio de la sesión de juego.
     *
     * @param start La nueva fecha y hora de inicio de la sesión de juego.
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Obtiene la fecha y hora de finalización de la sesión de juego.
     *
     * @return La fecha y hora de finalización de la sesión de juego.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Establece la fecha y hora de finalización de la sesión de juego.
     *
     * @param end La nueva fecha y hora de finalización de la sesión de juego.
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}

