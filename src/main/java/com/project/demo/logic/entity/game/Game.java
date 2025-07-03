package com.project.demo.logic.entity.game;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "game")
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private GameCategory category;

    @OneToMany(mappedBy = "game")
    private List<GameComponent> components;

    @OneToMany(mappedBy = "game")
    private List<GameFeedback> feedbacks;

    @OneToMany(mappedBy = "game")
    private List<GameSession> sessions;

    @OneToMany(mappedBy = "game")
    private List<GameReport> reports;

    @OneToMany(mappedBy = "game")
    private List<FavoriteGame> favoritedBy;

    @OneToMany(mappedBy = "game")
    private List<Streak> streaks;

    public Game() {
    }

    public Game(String name, String description, String difficulty, String imageUrl, String status, GameCategory category, List<GameComponent> components, List<GameFeedback> feedbacks, List<GameSession> sessions, List<GameReport> reports, List<FavoriteGame> favoritedBy, List<Streak> streaks) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
        this.status = status;
        this.category = category;
        this.components = components;
        this.feedbacks = feedbacks;
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
}
