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

    /**
     * Obtiene el ID del juego.
     *
     * @return El ID del juego.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el ID del juego.
     *
     * @param id El nuevo ID del juego.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del juego.
     *
     * @return El nombre del juego.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del juego.
     *
     * @param name El nuevo nombre del juego.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la descripción del juego.
     *
     * @return La descripción del juego.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece la descripción del juego.
     *
     * @param description La nueva descripción del juego.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtiene la dificultad del juego.
     *
     * @return La dificultad del juego.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Establece la dificultad del juego.
     *
     * @param difficulty La nueva dificultad del juego.
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Obtiene la URL de la imagen del juego.
     *
     * @return La URL de la imagen del juego.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Establece la URL de la imagen del juego.
     *
     * @param imageUrl La nueva URL de la imagen del juego.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Obtiene la categoría del juego.
     *
     * @return La categoría del juego.
     */
    public GameCategory getCategory() {
        return category;
    }

    /**
     * Establece la categoría del juego.
     *
     * @param category La nueva categoría del juego.
     */
    public void setCategory(GameCategory category) {
        this.category = category;
    }

    /**
     * Obtiene la lista de componentes del juego.
     *
     * @return La lista de componentes del juego.
     */
    public List<GameComponent> getComponents() {
        return components;
    }

    /**
     * Establece la lista de componentes del juego.
     *
     * @param components La nueva lista de componentes del juego.
     */
    public void setComponents(List<GameComponent> components) {
        this.components = components;
    }

    /**
     * Obtiene la lista de sesiones del juego.
     *
     * @return La lista de sesiones del juego.
     */
    public List<GameSession> getSessions() {
        return sessions;
    }

    /**
     * Establece la lista de sesiones del juego.
     *
     * @param sessions La nueva lista de sesiones del juego.
     */
    public void setSessions(List<GameSession> sessions) {
        this.sessions = sessions;
    }

    /**
     * Obtiene la lista de reportes del juego.
     *
     * @return La lista de reportes del juego.
     */
    public List<GameReport> getReports() {
        return reports;
    }

    /**
     * Establece la lista de reportes del juego.
     *
     * @param reports La nueva lista de reportes del juego.
     */
    public void setReports(List<GameReport> reports) {
        this.reports = reports;
    }

    /**
     * Obtiene la lista de usuarios que han marcado este juego como favorito.
     *
     * @return La lista de usuarios que han marcado este juego como favorito.
     */
    public List<FavoriteGame> getFavoritedBy() {
        return favoritedBy;
    }

    /**
     * Establece la lista de usuarios que han marcado este juego como favorito.
     *
     * @param favoritedBy La nueva lista de usuarios que han marcado este juego como favorito.
     */
    public void setFavoritedBy(List<FavoriteGame> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    /**
     * Obtiene la lista de rachas de juego.
     *
     * @return La lista de rachas de juego.
     */
    public List<Streak> getStreaks() {
        return streaks;
    }

    /**
     * Establece la lista de rachas de juego.
     *
     * @param streaks La nueva lista de rachas de juego.
     */
    public void setStreaks(List<Streak> streaks) {
        this.streaks = streaks;
    }
}

