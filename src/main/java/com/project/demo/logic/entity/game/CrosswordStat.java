package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.settings.LevelEnum;
import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "crossword_stats")
public class CrosswordStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String userEmail;

    @Column(nullable = false, length = 120)
    private String puzzleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LevelEnum difficulty;

    @Column(nullable = false)
    private Integer wordsFound;

    @Column(nullable = false)
    private Integer wordsTotal;

    @Column(nullable = false)
    private Integer mistakes;

    @Column(nullable = false)
    private Integer hints;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime finishedAt;

    @Column(nullable = false)
    private Long timeSeconds;

    @PrePersist @PreUpdate
    public void computeTimeSeconds() {
        if (startedAt != null && finishedAt != null) {
            this.timeSeconds = Math.max(0, Duration.between(startedAt, finishedAt).getSeconds());
        } else {
            this.timeSeconds = 0L;
        }
    }

    public Integer getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getPuzzleId() { return puzzleId; }
    public void setPuzzleId(String puzzleId) { this.puzzleId = puzzleId; }
    public LevelEnum getDifficulty() { return difficulty; }
    public void setDifficulty(LevelEnum difficulty) { this.difficulty = difficulty; }
    public Integer getWordsFound() { return wordsFound; }
    public void setWordsFound(Integer wordsFound) { this.wordsFound = wordsFound; }
    public Integer getWordsTotal() { return wordsTotal; }
    public void setWordsTotal(Integer wordsTotal) { this.wordsTotal = wordsTotal; }
    public Integer getMistakes() { return mistakes; }
    public void setMistakes(Integer mistakes) { this.mistakes = mistakes; }
    public Integer getHints() { return hints; }
    public void setHints(Integer hints) { this.hints = hints; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getFinishedAt() { return finishedAt; }
    public void setFinishedAt(LocalDateTime finishedAt) { this.finishedAt = finishedAt; }
    public Long getTimeSeconds() { return timeSeconds; }
    public void setTimeSeconds(Long timeSeconds) { this.timeSeconds = timeSeconds; }
}
