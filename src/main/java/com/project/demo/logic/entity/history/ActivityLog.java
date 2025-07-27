package com.project.demo.logic.entity.history;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa un registro de actividad de un usuario. Esta entidad mapea la tabla 'activity_logs' en
 * la base de datos.
 */
@Entity
@Table(name = "activity_logs")
public class ActivityLog {
    /** Identificador único del registro de actividad. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** El usuario que realizó la actividad. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** Descripción de la acción realizada. */
    @Column(length = 255)
    private String action;

    /** Fecha y hora en que se realizó la actividad. */
    private LocalDateTime date;

    /** Constructor por defecto. */
    public ActivityLog() {}

    /**
     * Constructor para crear una instancia de ActivityLog con todos los parámetros.
     *
     * @param id Identificador único.
     * @param user Usuario que realizó la actividad.
     * @param action Descripción de la acción.
     * @param date Fecha y hora de la actividad.
     */
    public ActivityLog(Integer id, User user, String action, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.action = action;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
