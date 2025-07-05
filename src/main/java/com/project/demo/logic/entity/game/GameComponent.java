package com.project.demo.logic.entity.game;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "game_components")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
