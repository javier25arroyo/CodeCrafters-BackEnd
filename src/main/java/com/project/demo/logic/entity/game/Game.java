package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

/**
 * Representa un juego en el sistema.
 * Esta entidad mapea la tabla 'games' en la base de datos.
 */
@Entity
@Table(name = "games")
public class Game {
    /**
     * Identificador único del juego.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del juego.
     */
    @Column(length = 100)
    private String name;

    /**
     * Descripción detallada del juego.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Nivel de dificultad del juego (ej. "fácil", "medio", "difícil").
     */
    @Column(length = 20)
    private String difficulty;

    /**
     * URL de la imagen representativa del juego.
     */
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;


    /**
     * Categoría a la que pertenece el juego.
     */
    @JsonManagedReference("game-category")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private GameCategory category;

    /**
     * Componentes individuales que conforman el juego (ej. preguntas, niveles).
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameComponent> components;

    /**
     * Sesiones de juego registradas para este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameSession> sessions;

    /**
     * Reportes generados para este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameReport> reports;

    /**
     * Lista de usuarios que han marcado este juego como favorito.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<FavoriteGame> favoritedBy;

    /**
     * Rachas de juego asociadas a este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Streak> streaks;

    /**
     * Constructor por defecto.
     */
    public Game() {
    }

    /**
     * Constructor para crear una instancia de Game con todos los parámetros.
     *
     * @param id          Identificador único.
     * @param name        Nombre del juego.
     * @param description Descripción del juego.
     * @param difficulty  Dificultad del juego.
     * @param imageUrl    URL de la imagen.
     * @param category    Categoría del juego.
     * @param components  Componentes del juego.
     * @param sessions    Sesiones de juego.
     * @param reports     Reportes del juego.
     * @param favoritedBy Usuarios que lo marcaron como favorito.
     * @param streaks     Rachas de juego.
     */
    public Game(Integer id, String name, String description, String difficulty, String imageUrl, GameCategory category, List<GameComponent> components, List<GameSession> sessions, List<GameReport> reports, List<FavoriteGame> favoritedBy, List<Streak> streaks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
        this.category = category;
        this.components = components;
        this.sessions = sessions;
        this.reports = reports;
        this.favoritedBy = favoritedBy;
        this.streaks = streaks;
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

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public GameCategory getCategory() {
        return category;
    }

    public void setCategory(GameCategory category) {
        this.category = category;
    }

    public List<GameComponent> getComponents() {
        return components;
    }

    public void setComponents(List<GameComponent> components) {
        this.components = components;
    }

    public List<GameSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<GameSession> sessions) {
        this.sessions = sessions;
    }

    public List<GameReport> getReports() {
        return reports;
    }

    public void setReports(List<GameReport> reports) {
        this.reports = reports;
    }

    public List<FavoriteGame> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(List<FavoriteGame> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    public List<Streak> getStreaks() {
        return streaks;
    }

    public void setStreaks(List<Streak> streaks) {
        this.streaks = streaks;
    }
}

