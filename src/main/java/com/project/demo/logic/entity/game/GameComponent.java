package com.project.demo.logic.entity.game;

import jakarta.persistence.*;

@Entity
@Table(name = "game_component")
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
}
