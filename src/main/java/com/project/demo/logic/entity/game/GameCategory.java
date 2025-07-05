package com.project.demo.logic.entity.game;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

@Entity
@Table(name = "game_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonBackReference("game-category")
    @OneToMany(mappedBy = "category")
    private List<Game> games;

    

    
}
