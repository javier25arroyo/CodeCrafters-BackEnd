package com.project.demo.logic.entity.notification;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

