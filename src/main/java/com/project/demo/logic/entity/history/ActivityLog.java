package com.project.demo.logic.entity.history;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa un registro de actividad de un usuario.
 * Esta entidad mapea la tabla 'activity_logs' en la base de datos.
 */
@Entity
@Table(name = "activity_logs")
public class ActivityLog {
    /**
     * Identificador único del registro de actividad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario que realizó la actividad.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Descripción de la acción realizada.
     */
    @Column(length = 255)
    private String action;

    /**
     * Fecha y hora en que se realizó la actividad.
     */
    private LocalDateTime date;

    /**
     * Constructor por defecto.
     */
    public ActivityLog() {
    }

    /**
     * Constructor para crear una instancia de ActivityLog con todos los parámetros.
     *
     * @param id     Identificador único.
     * @param user   Usuario que realizó la actividad.
     * @param action Descripción de la acción.
     * @param date   Fecha y hora de la actividad.
     */
    public ActivityLog(Integer id, User user, String action, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.action = action;
        this.date = date;
    }

    /**
     * Obtiene el ID del registro de actividad.
     *
     * @return El ID del registro de actividad.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del registro de actividad.
     *
     * @param id El nuevo ID del registro de actividad.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que realizó la actividad.
     *
     * @return El usuario que realizó la actividad.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que realizó la actividad.
     *
     * @param user El nuevo usuario que realizó la actividad.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene la acción realizada.
     *
     * @return La acción realizada.
     */
    public String getAction() {
        return action;
    }

    /**
     * Establece la acción realizada.
     *
     * @param action La nueva acción realizada.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Obtiene la fecha y hora en que se realizó la actividad.
     *
     * @return La fecha y hora en que se realizó la actividad.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha y hora en que se realizó la actividad.
     *
     * @param date La nueva fecha y hora en que se realizó la actividad.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
