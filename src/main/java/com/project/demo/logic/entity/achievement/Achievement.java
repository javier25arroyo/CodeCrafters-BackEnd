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

    /**
     * Obtiene el ID del logro.
     *
     * @return El ID del logro.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del logro.
     *
     * @param id El nuevo ID del logro.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del logro.
     *
     * @return El nombre del logro.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del logro.
     *
     * @param name El nuevo nombre del logro.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripción del logro.
     *
     * @return La descripción del logro.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción del logro.
     *
     * @param description La nueva descripción del logro.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene la lista de logros de usuario.
     *
     * @return La lista de logros de usuario.
     */
    public List<UserAchievement> getUserAchievements() {
        return userAchievements;
    }

    /**
     * Establece la lista de logros de usuario.
     *
     * @param userAchievements La nueva lista de logros de usuario.
     */
    public void setUserAchievements(List<UserAchievement> userAchievements) {
        this.userAchievements = userAchievements;
    }
}
