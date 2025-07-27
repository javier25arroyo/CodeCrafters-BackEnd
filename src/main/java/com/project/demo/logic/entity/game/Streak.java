package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa una racha de juego para un usuario y un juego específicos. Esta entidad mapea la tabla
 * 'streaks' en la base de datos.
 */
@Entity
@Table(name = "streaks")
public class Streak {
    /** Identificador único de la racha. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** El usuario al que pertenece esta racha. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** El juego al que se refiere esta racha. */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /** La racha actual del usuario para este juego. */
    @Column(name = "current_streak")
    private Integer currentStreak;

    /** La racha máxima alcanzada por el usuario para este juego. */
    @Column(name = "max_streak")
    private Integer maxStreak;

    /** Constructor por defecto. */
    public Streak() {}

    /**
     * Constructor para crear una instancia de Streak con todos los parámetros.
     *
     * @param id Identificador único.
     * @param user Usuario asociado.
     * @param game Juego asociado.
     * @param currentStreak Racha actual.
     * @param maxStreak Racha máxima.
     */
    public Streak(Integer id, User user, Game game, Integer currentStreak, Integer maxStreak) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
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

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Integer getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(Integer maxStreak) {
        this.maxStreak = maxStreak;
    }
}
