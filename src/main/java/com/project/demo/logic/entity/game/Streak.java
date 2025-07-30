package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa una racha de juego para un usuario y un juego específicos.
 * Esta entidad mapea la tabla 'streaks' en la base de datos.
 */
@Entity
@Table(name = "streaks")
public class Streak {
    /**
     * Identificador único de la racha.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario al que pertenece esta racha.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El juego al que se refiere esta racha.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * La racha actual del usuario para este juego.
     */
    @Column(name = "current_streak")
    private Integer currentStreak;

    /**
     * La racha máxima alcanzada por el usuario para este juego.
     */
    @Column(name = "max_streak")
    private Integer maxStreak;

    /**
     * Constructor por defecto.
     */
    public Streak() {
    }

    /**
     * Constructor para crear una instancia de Streak con todos los parámetros.
     *
     * @param id            Identificador único.
     * @param user          Usuario asociado.
     * @param game          Juego asociado.
     * @param currentStreak Racha actual.
     * @param maxStreak     Racha máxima.
     */
    public Streak(Integer id, User user, Game game, Integer currentStreak, Integer maxStreak) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
    }

    /**
     * Obtiene el ID de la racha.
     *
     * @return El ID de la racha.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la racha.
     *
     * @param id El nuevo ID de la racha.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario al que pertenece esta racha.
     *
     * @return El usuario al que pertenece esta racha.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario al que pertenece esta racha.
     *
     * @param user El nuevo usuario al que pertenece esta racha.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el juego al que se refiere esta racha.
     *
     * @return El juego al que se refiere esta racha.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece el juego al que se refiere esta racha.
     *
     * @param game El nuevo juego al que se refiere esta racha.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Obtiene la racha actual del usuario para este juego.
     *
     * @return La racha actual del usuario para este juego.
     */
    public Integer getCurrentStreak() {
        return currentStreak;
    }

    /**
     * Establece la racha actual del usuario para este juego.
     *
     * @param currentStreak La nueva racha actual del usuario para este juego.
     */
    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    /**
     * Obtiene la racha máxima alcanzada por el usuario para este juego.
     *
     * @return La racha máxima alcanzada por el usuario para este juego.
     */
    public Integer getMaxStreak() {
        return maxStreak;
    }

    /**
     * Establece la racha máxima alcanzada por el usuario para este juego.
     *
     * @param maxStreak La nueva racha máxima alcanzada por el usuario para este juego.
     */
    public void setMaxStreak(Integer maxStreak) {
        this.maxStreak = maxStreak;
    }
}

