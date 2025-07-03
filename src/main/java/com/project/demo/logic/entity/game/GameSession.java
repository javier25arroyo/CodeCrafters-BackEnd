package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.auth.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_session")
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

    public GameSession() {
    }

    public GameSession(User user, Game game, String status, Integer totalDuration, Integer partialScore, LocalDateTime start, LocalDateTime end) {
        this.user = user;
        this.game = game;
        this.status = status;
        this.totalDuration = totalDuration;
        this.partialScore = partialScore;
        this.start = start;
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Integer getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Integer partialScore) {
        this.partialScore = partialScore;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
