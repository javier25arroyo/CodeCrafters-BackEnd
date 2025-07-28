package com.project.demo.logic.entity.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa una sugerencia enviada por un usuario.
 * Esta entidad mapea la tabla 'suggestions' en la base de datos.
 */
@Entity
@Table(name = "suggestions")
public class Suggestion {

    /**
     * Identificador único de la sugerencia.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario que envió la sugerencia.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    /**
     * El contenido del mensaje de la sugerencia.
     */
    @Column(columnDefinition = "TEXT")
    private String message;

    /**
     * El estado actual de la sugerencia (ej. "pendiente", "revisada", "implementada").
     */
    @Column(length = 50)
    private String status;

    /**
     * La fecha y hora en que se envió la sugerencia.
     */
    private LocalDateTime date;

    /**
     * Constructor por defecto.
     */
    public Suggestion() {
    }

    /**
     * Constructor para crear una instancia de Suggestion con todos los parámetros.
     *
     * @param id      Identificador único.
     * @param user    Usuario que envió la sugerencia.
     * @param message Contenido de la sugerencia.
     * @param status  Estado de la sugerencia.
     * @param date    Fecha de la sugerencia.
     */
    public Suggestion(Integer id, User user, String message, String status, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.status = status;
        this.date = date;
    }

    /**
     * Obtiene el ID de la sugerencia.
     *
     * @return El ID de la sugerencia.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la sugerencia.
     *
     * @param id El nuevo ID de la sugerencia.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que envió la sugerencia.
     *
     * @return El usuario que envió la sugerencia.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que envió la sugerencia.
     *
     * @param user El nuevo usuario que envió la sugerencia.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el contenido del mensaje de la sugerencia.
     *
     * @return El contenido del mensaje de la sugerencia.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Establece el contenido del mensaje de la sugerencia.
     *
     * @param message El nuevo contenido del mensaje de la sugerencia.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtiene el estado actual de la sugerencia.
     *
     * @return El estado actual de la sugerencia.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado actual de la sugerencia.
     *
     * @param status El nuevo estado actual de la sugerencia.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtiene la fecha y hora en que se envió la sugerencia.
     *
     * @return La fecha y hora en que se envió la sugerencia.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha y hora en que se envió la sugerencia.
     *
     * @param date La nueva fecha y hora en que se envió la sugerencia.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
