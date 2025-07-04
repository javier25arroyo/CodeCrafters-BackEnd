package com.project.demo.logic.entity.game;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    

    
}
