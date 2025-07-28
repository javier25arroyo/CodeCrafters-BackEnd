package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

/**
 * Representa un juego marcado como favorito por un usuario.
 * Esta entidad mapea la tabla 'favorite_games' en la base de datos.
 */
@Entity
@Table(name = "favorite_games")
public class FavoriteGame {
    /**
     * Identificador único del registro de juego favorito.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario que marcó el juego como favorito.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El juego que ha sido marcado como favorito.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * Constructor por defecto.
     */
    public FavoriteGame() {
    }

    /**
     * Constructor para crear una instancia de FavoriteGame con todos los parámetros.
     *
     * @param id   Identificador único.
     * @param user Usuario que marcó el juego como favorito.
     * @param game Juego favorito.
     */
    public FavoriteGame(Integer id, User user, Game game) {
        this.id = id;
        this.user = user;
        this.game = game;
    }

    /**
     * Obtiene el ID del juego favorito.
     *
     * @return El ID del juego favorito.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del juego favorito.
     *
     * @param id El nuevo ID del juego favorito.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que marcó el juego como favorito.
     *
     * @return El usuario que marcó el juego como favorito.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que marcó el juego como favorito.
     *
     * @param user El nuevo usuario que marcó el juego como favorito.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el juego que ha sido marcado como favorito.
     *
     * @return El juego que ha sido marcado como favorito.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Establece el juego que ha sido marcado como favorito.
     *
     * @param game El nuevo juego que ha sido marcado como favorito.
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
