package com.project.demo.logic.entity.settings;

import jakarta.persistence.*;

/**
 * Representa un nivel en el sistema.
 * Esta entidad mapea la tabla 'levels' en la base de datos.
 */
@Entity
@Table(name = "levels")
public class Level {
    /**
     * Identificador único del nivel.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del nivel.
     */
    private String name;
    /**
     * Puntos requeridos para alcanzar este nivel.
     */
    private int points_required;

    /**
     * Constructor por defecto.
     */
    public Level() {
    }

    /**
     * Constructor para crear una instancia de Level con todos los parámetros.
     *
     * @param id              Identificador único del nivel.
     * @param name            Nombre del nivel.
     * @param points_required Puntos necesarios para alcanzar este nivel.
     */
    public Level(Long id, String name, int points_required) {
        this.id = id;
        this.name = name;
        this.points_required = points_required;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints_required() {
        return points_required;
    }

    public void setPoints_required(int points_required) {
        this.points_required = points_required;
    }
}