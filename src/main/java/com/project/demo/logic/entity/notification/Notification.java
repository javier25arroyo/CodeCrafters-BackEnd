package com.project.demo.logic.entity.notification;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa una notificación para un usuario.
 * Esta entidad mapea la tabla 'notifications' en la base de datos.
 */
@Entity
@Table(name = "notifications")
public class Notification {
    /**
     * Identificador único de la notificación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario al que está dirigida la notificación.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El contenido del mensaje de la notificación.
     */
    @Column(columnDefinition = "TEXT")
    private String message;

    /**
     * La fecha y hora en que se creó la notificación.
     */
    private LocalDateTime date;

    /**
     * Indica si la notificación ha sido leída por el usuario.
     */
    @Column(name = "is_read")
    private Boolean read = false;

    /**
     * Constructor por defecto.
     */
    public Notification() {
    }

    /**
     * Constructor para crear una instancia de Notification con todos los parámetros.
     *
     * @param id      Identificador único.
     * @param user    Usuario al que se dirige la notificación.
     * @param message Contenido de la notificación.
     * @param date    Fecha de la notificación.
     * @param read    Estado de lectura de la notificación.
     */
    public Notification(Integer id, User user, String message, LocalDateTime date, Boolean read) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.date = date;
        this.read = read;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
