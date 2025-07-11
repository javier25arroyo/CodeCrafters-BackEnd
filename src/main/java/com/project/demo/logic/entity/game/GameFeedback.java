package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa un comentario o valoración de un juego por parte de un usuario.
 * Esta entidad mapea la tabla 'game_feedbacks' en la base de datos.
 */
@Entity
@Table(name = "game_feedbacks")
public class GameFeedback {
    /**
     * Identificador único del comentario del juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El juego al que se refiere este comentario.
     */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**
     * El usuario que realizó el comentario.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El contenido del comentario.
     */
    @Column(columnDefinition = "TEXT")
    private String comment;

    /**
     * La calificación o puntuación dada al juego (ej. de 1 a 5).
     */
    private Integer rating;

    /**
     * La fecha y hora en que se realizó el comentario.
     */
    private LocalDateTime date;

    /**
     * Constructor por defecto.
     */
    public GameFeedback() {
    }

    /**
     * Constructor para crear una instancia de GameFeedback con todos los parámetros.
     *
     * @param id      Identificador único.
     * @param game    Juego asociado.
     * @param user    Usuario que realizó el comentario.
     * @param comment Contenido del comentario.
     * @param rating  Calificación del juego.
     * @param date    Fecha del comentario.
     */
    public GameFeedback(Integer id, Game game, User user, String comment, Integer rating, LocalDateTime date) {
        this.id = id;
        this.game = game;
        this.user = user;
        this.comment = comment;
        this.rating = rating;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
