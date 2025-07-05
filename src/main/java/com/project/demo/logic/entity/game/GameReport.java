package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private Integer duration;

    private Integer score;

    @Column(length = 50)
    private String result;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    private LocalDateTime date;

    

    
}
