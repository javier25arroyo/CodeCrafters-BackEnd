package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "game_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(length = 20)
    private String status;

    @Column(name = "total_duration")
    private Integer totalDuration;

    @Column(name = "partial_score")
    private Integer partialScore;

    private LocalDateTime start;

    private LocalDateTime end;

    

    
}
