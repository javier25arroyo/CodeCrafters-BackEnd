package com.project.demo.logic.entity.game;

import jakarta.persistence.*;

@Entity
@Table(name = "game_components")
public class GameComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(length = 50)
    private String type;

    @Column(columnDefinition = "TEXT")
    private String content;

    public GameComponent() {
    }

    public GameComponent(Integer id, Game game, String type, String content) {
        this.id = id;
        this.game = game;
        this.type = type;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
