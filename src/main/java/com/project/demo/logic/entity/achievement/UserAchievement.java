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

    /**
     * Obtiene el ID de la relación usuario-logro.
     *
     * @return El ID de la relación usuario-logro.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la relación usuario-logro.
     *
     * @param id El nuevo ID de la relación usuario-logro.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el usuario que ha obtenido el logro.
     *
     * @return El usuario que ha obtenido el logro.
     */
    public User getUser() {
        return user;
    }

    /**
     * Establece el usuario que ha obtenido el logro.
     *
     * @param user El nuevo usuario que ha obtenido el logro.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Obtiene el logro que ha sido obtenido por el usuario.
     *
     * @return El logro que ha sido obtenido por el usuario.
     */
    public Achievement getAchievement() {
        return achievement;
    }

    /**
     * Establece el logro que ha sido obtenido por el usuario.
     *
     * @param achievement El nuevo logro que ha sido obtenido por el usuario.
     */
    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    /**
     * Obtiene la fecha y hora en que el usuario obtuvo el logro.
     *
     * @return La fecha y hora en que el usuario obtuvo el logro.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Establece la fecha y hora en que el usuario obtuvo el logro.
     *
     * @param date La nueva fecha y hora en que el usuario obtuvo el logro.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
