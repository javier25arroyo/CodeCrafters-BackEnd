package com.project.demo.logic.entity.settings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.demo.logic.entity.game.Game;
import jakarta.persistence.*;

/**
 * Representa un nivel en el sistema.
 * Esta entidad mapea la tabla 'levels' en la base de datos.
 */
@Entity
@Table(name = "levels")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonBackReference
    @OneToOne(mappedBy = "level")
    private Game game;

    @Column(nullable = false)
    private boolean active = true;

    /**
     * Constructor por defecto.
     */
    public Level() {
    }

    public Level(Long id, String name, String description, Game game) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.game = game;
    }

    // Getters y setters...

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}