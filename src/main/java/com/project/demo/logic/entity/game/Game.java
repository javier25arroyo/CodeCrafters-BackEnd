package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

/**
 * Representa un juego en el sistema.
 * Esta entidad mapea la tabla 'games' en la base de datos.
 */
@Entity
@Table(name = "games")
public class Game {
    /**
     * Identificador único del juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * Tipo de juego según la enumeración GameTypeEnum.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameTypeEnum gameType;


    /**
     * Nivel de dificultad del juego (ej. "fácil", "medio", "difícil").
     */
    @Column(length = 20)
    private String difficulty;


    /**
     * Sesiones de juego registradas para este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameSession> sessions;

    /**
     * Reportes generados para este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameReport> reports;


    /**
     * Rachas de juego asociadas a este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Streak> streaks;

    /**
     * Constructor por defecto.
     */
    public Game() {
    }

    /**
     * Constructor para crear una instancia de Game con todos los parámetros.
     *
     * @param id          Identificador único.
     * @param gameType    Tipo de juego.
     * @param difficulty  Dificultad del juego.
     * @param sessions    Sesiones de juego.
     * @param reports     Reportes del juego.
     * @param streaks     Rachas de juego.
     */
    public Game(Integer id, GameTypeEnum gameType, String difficulty, List<GameSession> sessions, List<GameReport> reports, List<Streak> streaks) {
        this.id = id;
        this.gameType = gameType;
        this.difficulty = difficulty;
        this.sessions = sessions;
        this.reports = reports;
        this.streaks = streaks;
    }

    /**
     * Obtiene el ID del juego.
     *
     * @return El ID del juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del juego.
     *
     * @param id El nuevo ID del juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de juego.
     *
     * @return El tipo de juego.
     */
    public GameTypeEnum getGameType() {
        return gameType;
    }

    /**
     * Establece el tipo de juego.
     *
     * @param gameType El nuevo tipo de juego.
     */
    public void setGameType(GameTypeEnum gameType) {
        this.gameType = gameType;
    }


    /**
     * Obtiene la dificultad del juego.
     *
     * @return La dificultad del juego.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Establece la dificultad del juego.
     *
     * @param difficulty La nueva dificultad del juego.
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    /**
     * Obtiene la lista de sesiones del juego.
     *
     * @return La lista de sesiones del juego.
     */
    public List<GameSession> getSessions() {
        return sessions;
    }

    /**
     * Establece la lista de sesiones del juego.
     *
     * @param sessions La nueva lista de sesiones del juego.
     */
    public void setSessions(List<GameSession> sessions) {
        this.sessions = sessions;
    }

    /**
     * Obtiene la lista de reportes del juego.
     *
     * @return La lista de reportes del juego.
     */
    public List<GameReport> getReports() {
        return reports;
    }

    /**
     * Establece la lista de reportes del juego.
     *
     * @param reports La nueva lista de reportes del juego.
     */
    public void setReports(List<GameReport> reports) {
        this.reports = reports;
    }

    /**
     * Obtiene la lista de rachas de juego.
     *
     * @return La lista de rachas de juego.
     */
    public List<Streak> getStreaks() {
        return streaks;
    }

    /**
     * Establece la lista de rachas de juego.
     *
     * @param streaks La nueva lista de rachas de juego.
     */
    public void setStreaks(List<Streak> streaks) {
        this.streaks = streaks;
    }
}

