package com.project.demo.rest.game;

import com.project.demo.logic.entity.settings.LevelEnum;
import java.time.LocalDateTime;

public class CrosswordStatRequest {
    public String puzzleId;
    public LevelEnum difficulty;
    public Integer wordsFound;
    public Integer wordsTotal;
    public Integer mistakes;
    public Integer hints;
    public Boolean completed;
    public LocalDateTime startedAt;
    public LocalDateTime finishedAt;
}