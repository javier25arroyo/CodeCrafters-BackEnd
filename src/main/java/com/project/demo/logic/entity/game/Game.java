package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.settings.LevelEnum;
import jakarta.persistence.*;
import java.util.List;

/**
 * Representa un juego en el sistema.
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
     * Tipo de juego.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameTypeEnum gameType;

    /**
     * Nivel del juego.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private LevelEnum level;

    /**
     * Puntuaciones asociadas a este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Score> scores;

    /**
     * Constructor por defecto.
     */
    public Game() {
    }

    /**
     * Constructor con todos los parámetros para inicializar un objeto Game.
     * @param id Identificador único.
     * @param gameType Tipo de juego.
     * @param level Nivel del juego.
     * @param scores Puntuaciones asociadas al juego.
     */
    public Game(Integer id, GameTypeEnum gameType, LevelEnum level, List<Score> scores) {
        this.id = id;
        this.gameType = gameType;
        this.level = level;
        this.scores = scores;
    }

    /**
     * Obtiene el ID del juego.
     * @return El ID del juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del juego.
     * @param id El nuevo ID del juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo de juego.
     * @return El tipo de juego.
     */
    public GameTypeEnum getGameType() {
        return gameType;
    }

    /**
     * Establece el tipo de juego.
     * @param gameType El nuevo tipo de juego.
     */
    public void setGameType(GameTypeEnum gameType) {
        this.gameType = gameType;
    }

    /**
     * Obtiene el nivel del juego.
     * @return El nivel del juego.
     */
    public LevelEnum getLevel() {
        return level;
    }

    /**
     * Establece el nivel del juego.
     * @param level El nuevo nivel del juego.
     */
    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    /**
     * Obtiene las puntuaciones asociadas a este juego.
     * @return Las puntuaciones asociadas a este juego.
     */
    public List<Score> getScores() {
        return scores;
    }

    /**
     * Establece las puntuaciones asociadas a este juego.
     * @param scores Las nuevas puntuaciones asociadas a este juego.
     */
    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
