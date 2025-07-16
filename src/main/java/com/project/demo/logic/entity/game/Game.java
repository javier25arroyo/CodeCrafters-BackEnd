package com.project.demo.logic.entity.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.project.demo.logic.entity.settings.Level;
import java.util.List;

/**
 * Representa un juego en el sistema.
 * Esta entidad mapea la tabla 'games' en la base de datos.
 */
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 20)
    private String difficulty;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(length = 20)
    private String status;

    @JsonManagedReference("game-category")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private GameCategory category;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameComponent> components;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameFeedback> feedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameSession> sessions;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<GameReport> reports;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<FavoriteGame> favoritedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "game")
    private List<Streak> streaks;

    /**
     * Niveles asociados a este juego.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Level> levels;


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
     * @param imageUrl    URL de la imagen.
     * @param status      Estado del juego.
     * @param category    Categoría del juego.
     * @param components  Componentes del juego.
     * @param feedbacks   Comentarios del juego.
     * @param sessions    Sesiones de juego.
     * @param reports     Reportes del juego.
     * @param favoritedBy Usuarios que lo marcaron como favorito.
     * @param streaks     Rachas de juego.
     */
    public Game(Integer id, String name, String description, String imageUrl, String status, GameCategory category, List<GameComponent> components, List<GameFeedback> feedbacks, List<GameSession> sessions, List<GameReport> reports, List<FavoriteGame> favoritedBy, List<Streak> streaks, List<Level> levels) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.status = status;
        this.category = category;
        this.components = components;
        this.feedbacks = feedbacks;
        this.sessions = sessions;
        this.reports = reports;
        this.favoritedBy = favoritedBy;
        this.streaks = streaks;
        this.levels = levels;
    }

    // Getters and setters...

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


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<GameFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<GameFeedback> feedbacks) {
        this.feedbacks = feedbacks;
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

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels=levels;
    }

}