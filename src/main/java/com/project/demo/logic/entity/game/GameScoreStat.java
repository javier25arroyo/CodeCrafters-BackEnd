package com.project.demo.logic.entity.game;

public class GameScoreStat {
    private GameTypeEnum gameType;
    private Double maxScore;

    public GameScoreStat(GameTypeEnum gameType, Double maxScore) {
        this.gameType = gameType;
        this.maxScore = maxScore;
    }

    public GameTypeEnum getGameType() {
        return gameType;
    }

    public void setGameType(GameTypeEnum gameType) {
        this.gameType = gameType;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }
}
