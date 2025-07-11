package com.project.demo.logic.entity.achievement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa la relación entre un usuario y un logro, indicando que un usuario ha obtenido un logro.
 * Esta entidad mapea la tabla 'user_achievements' en la base de datos.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "user_achievements")
public class UserAchievement {
    /**
     * Identificador único de la relación usuario-logro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * El usuario que ha obtenido el logro.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * El logro que ha sido obtenido por el usuario.
     */
    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    /**
     * La fecha y hora en que el usuario obtuvo el logro.
     */
    private LocalDateTime date;

    /**
     * Constructor por defecto.
     */
    public UserAchievement() {
    }

    /**
     * Constructor para crear una instancia de UserAchievement con todos los parámetros.
     *
     * @param id          Identificador único.
     * @param user        Usuario que obtuvo el logro.
     * @param achievement Logro obtenido.
     * @param date        Fecha en que se obtuvo el logro.
     */
    public UserAchievement(Integer id, User user, Achievement achievement, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.achievement = achievement;
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

    public Achievement getAchievement() {
        return achievement;
    }

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
