package com.project.demo.logic.entity.game.games;

import com.project.demo.logic.entity.settings.LevelEnum;

public class PuzzleResultDTO {
    private int time;
    private int movements;
    private LevelEnum difficulty;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public LevelEnum getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(LevelEnum difficulty) {
        this.difficulty = difficulty;
    }
}
