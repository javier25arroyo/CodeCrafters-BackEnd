package com.project.demo.rest.game;

import com.project.demo.logic.entity.settings.LevelEnum;
import java.util.Map;

public class CrosswordSummaryResponse {
    public long totalStarted;
    public long totalCompleted;
    public double avgTimeSeconds;
    public long wordsFoundTotal;
    public long wordsTotalTotal;
    public long mistakesTotal;
    public Map<LevelEnum, Long> byDifficulty;

    public CrosswordSummaryResponse(long totalStarted, long totalCompleted, double avgTimeSeconds,
                                    long wordsFoundTotal, long wordsTotalTotal, long mistakesTotal,
                                    Map<LevelEnum, Long> byDifficulty) {
        this.totalStarted = totalStarted;
        this.totalCompleted = totalCompleted;
        this.avgTimeSeconds = avgTimeSeconds;
        this.wordsFoundTotal = wordsFoundTotal;
        this.wordsTotalTotal = wordsTotalTotal;
        this.mistakesTotal = mistakesTotal;
        this.byDifficulty = byDifficulty;
    }
}