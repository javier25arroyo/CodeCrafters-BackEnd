package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * Representa una categoría de juego.
 * Esta entidad mapea la tabla 'game_categories' en la base de datos.
 */
@Entity
@Table(name = "game_categories")
public class GameCategory {
    /**
     * Identificador único de la categoría de juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la categoría. No puede ser nulo.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Lista de juegos que pertenecen a esta categoría.
     */
    @JsonBackReference("game-category")
    @OneToMany(mappedBy = "category")
    private List<Game> games;

    /**
     * Constructor por defecto.
     */
    public GameCategory() {
    }

    /**
     * Constructor para crear una instancia de GameCategory con todos los parámetros.
     *
     * @param id    Identificador único.
     * @param name  Nombre de la categoría.
     * @param games Lista de juegos asociados a esta categoría.
     */
    public GameCategory(Integer id, String name, List<Game> games) {
        this.id = id;
        this.name = name;
        this.games = games;
    }

    /**
     * Obtiene el ID de la categoría de juego.
     *
     * @return El ID de la categoría de juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID de la categoría de juego.
     *
     * @param id El nuevo ID de la categoría de juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la categoría de juego.
     *
     * @return El nombre de la categoría de juego.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre de la categoría de juego.
     *
     * @param name El nuevo nombre de la categoría de juego.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la lista de juegos asociados a esta categoría.
     *
     * @return La lista de juegos asociados a esta categoría.
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * Establece la lista de juegos asociados a esta categoría.
     *
     * @param games La nueva lista de juegos asociados a esta categoría.
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }
}
