package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "favorite_games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    

    
}
