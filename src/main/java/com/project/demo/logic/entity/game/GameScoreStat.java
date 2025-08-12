package com.project.demo.logic.entity.game;

public class GameScoreStat {
    private GameTypeEnum gameType;
    private Double totalScore;

    public GameScoreStat(GameTypeEnum gameType, Double totalScore) {
        this.gameType = gameType;
        this.totalScore = totalScore;
    }

    public GameTypeEnum getGameType() {
        return gameType;
    }

    public void setGameType(GameTypeEnum gameType) {
        this.gameType = gameType;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
