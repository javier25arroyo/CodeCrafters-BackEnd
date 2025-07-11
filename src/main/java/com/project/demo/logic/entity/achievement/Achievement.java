package com.project.demo.logic.entity.achievement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Representa un logro que los usuarios pueden obtener en el sistema.
 * Esta entidad mapea la tabla 'achievements' en la base de datos.
 */
@Entity
@Table(name = "achievements")
public class Achievement {
    /**
     * Identificador único del logro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del logro.
     */
    @Column(length = 100)
    private String name;

    /**
     * Descripción detallada del logro.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Lista de relaciones entre este logro y los usuarios que lo han obtenido.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "achievement")
    private List<UserAchievement> userAchievements;

    /**
     * Constructor por defecto.
     */
    public Achievement() {
    }

    /**
     * Constructor para crear una instancia de Achievement con todos los parámetros.
     *
     * @param id             Identificador único.
     * @param name           Nombre del logro.
     * @param description    Descripción del logro.
     * @param userAchievements Lista de usuarios que han obtenido este logro.
     */
    public Achievement(Integer id, String name, String description, List<UserAchievement> userAchievements) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userAchievements = userAchievements;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserAchievement> getUserAchievements() {
        return userAchievements;
    }

    public void setUserAchievements(List<UserAchievement> userAchievements) {
        this.userAchievements = userAchievements;
    }
}
