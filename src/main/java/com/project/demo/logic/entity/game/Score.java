package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.settings.LevelEnum;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import java.util.Date;

/**
 * Representa la puntuación de un usuario en un juego.
 */
@Entity
@Table(name = "scores")
public class Score {
    /**
     * Identificador único de la puntuación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario que obtuvo la puntuación.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Juego en el que se obtuvo la puntuación.
     */
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

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
     * Número de movimientos realizados en el juego.
     */
    @Column(nullable = false)
    private Integer movements;

    /**
     * Tiempo que tomó completar el juego (en segundos).
     */
    @Column(nullable = false)
    private Long time;

    /**
     * Puntuación obtenida.
     */
    @Column(nullable = false)
    private Double score;

    /**
     * Fecha en que se obtuvo la puntuación.
     */
    @Column(nullable = false)
    private Date date;

    /**
     * Constructor por defecto.
     */
    public Score() {
    }

    /**
     * Constructor con todos los parámetros para inicializar un objeto Score.
     * @param user Usuario que obtuvo la puntuación.
     * @param game Juego en el que se obtuvo la puntuación.
     * @param gameType Tipo de juego.
     * @param level Nivel del juego.
     * @param movements Número de movimientos realizados.
     * @param time Tiempo que tomó completar el juego.
     * @param score Puntuación obtenida.
     * @param date Fecha en que se obtuvo la puntuación.
     */
    public Score(User user, Game game, GameTypeEnum gameType, LevelEnum level, Integer movements, Long time, Double score, Date date) {
        this.user = user;
        this.game = game;
        this.gameType = gameType;
        this.level = level;
        this.movements = movements;
        this.time = time;
        this.score = score;
        this.date = date;
    }

    /**
     * Obtiene el ID de la puntuación.
     * @return El ID de la puntuación.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la puntuación.
     * @param id El nuevo ID de la puntuación.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que obtuvo la puntuación.
     * @return El usuario que obtuvo la puntuación.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que obtuvo la puntuación.
     * @param user El nuevo usuario que obtuvo la puntuación.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el juego en el que se obtuvo la puntuación.
     * @return El juego en el que se obtuvo la puntuación.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece el juego en el que se obtuvo la puntuación.
     * @param game El nuevo juego en el que se obtuvo la puntuación.
     */
    public void setGame(Game game) {
        this.game = game;
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
     * Obtiene el número de movimientos realizados en el juego.
     * @return El número de movimientos realizados en el juego.
     */
    public Integer getMovements() {
        return movements;
    }

    /**
     * Establece el número de movimientos realizados en el juego.
     * @param movements El nuevo número de movimientos realizados en el juego.
     */
    public void setMovements(Integer movements) {
        this.movements = movements;
    }

    /**
     * Obtiene el tiempo que tomó completar el juego.
     * @return El tiempo que tomó completar el juego.
     */
    public Long getTime() {
        return time;
    }

    /**
     * Establece el tiempo que tomó completar el juego.
     * @param time El nuevo tiempo que tomó completar el juego.
     */
    public void setTime(Long time) {
        this.time = time;
    }

    /**
     * Obtiene la puntuación obtenida.
     * @return La puntuación obtenida.
     */
    public Double getScore() {
        return score;
    }

    /**
     * Establece la puntuación obtenida.
     * @param score La nueva puntuación obtenida.
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * Obtiene la fecha en que se obtuvo la puntuación.
     * @return La fecha en que se obtuvo la puntuación.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Establece la fecha en que se obtuvo la puntuación.
     * @param date La nueva fecha en que se obtuvo la puntuación.
     */
    public void setDate(Date date) {
        this.date=date;
}
}