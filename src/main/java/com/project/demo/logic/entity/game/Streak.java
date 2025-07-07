package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "streaks")
public class Streak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "current_streak")
    private Integer currentStreak;

    @Column(name = "max_streak")
    private Integer maxStreak;

    public Streak() {
    }

    public Streak(Integer id, User user, Game game, Integer currentStreak, Integer maxStreak) {
        this.id = id;
        this.user = user;
        this.game = game;
        this.currentStreak = currentStreak;
        this.maxStreak = maxStreak;
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

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Integer getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(Integer maxStreak) {
        this.maxStreak = maxStreak;
    }
}
