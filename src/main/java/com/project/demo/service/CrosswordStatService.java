package com.project.demo.service;

import com.project.demo.logic.entity.game.CrosswordStat;
import com.project.demo.logic.entity.game.repository.CrosswordStatRepository;
import com.project.demo.logic.entity.settings.LevelEnum;
import com.project.demo.rest.game.CrosswordStatRequest;
import com.project.demo.rest.game.CrosswordSummaryResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class CrosswordStatService {

    private final CrosswordStatRepository repo;

    public CrosswordStatService(CrosswordStatRepository repo) {
        this.repo = repo;
    }

    public CrosswordStat save(CrosswordStatRequest req, Authentication auth) {
        CrosswordStat c = new CrosswordStat();
        c.setUserEmail(auth.getName());
        c.setPuzzleId(req.puzzleId);
        c.setDifficulty(req.difficulty);
        c.setWordsFound(nz(req.wordsFound));
        c.setWordsTotal(nz(req.wordsTotal));
        c.setMistakes(nz(req.mistakes));
        c.setHints(nz(req.hints));
        c.setCompleted(Boolean.TRUE.equals(req.completed));
        c.setStartedAt(req.startedAt);
        c.setFinishedAt(req.finishedAt);
        // @PrePersist actualizará timeSeconds
        return repo.save(c);
    }

    public CrosswordSummaryResponse summary(LocalDate from, LocalDate to) {
        LocalDateTime f = from != null ? from.atStartOfDay() : LocalDate.now().minusDays(30).atStartOfDay();
        LocalDateTime t = to   != null ? to.plusDays(1).atStartOfDay() : LocalDate.now().plusDays(1).atStartOfDay();

        long totalStarted   = repo.countByStartedAtBetween(f, t);
        long totalCompleted = repo.countByCompletedIsTrueAndStartedAtBetween(f, t);
        double avgTime      = repo.avgTimeSecondsBetween(f, t);

        Object[] totals = repo.totalsBetween(f, t);
        long wordsFoundTotal = ((Number) totals[0]).longValue();
        long wordsTotalTotal = ((Number) totals[1]).longValue();
        long mistakesTotal   = ((Number) totals[2]).longValue();

        List<Object[]> byDiffRaw = repo.countByDifficultyBetween(f, t);
        Map<LevelEnum, Long> byDifficulty = new EnumMap<>(LevelEnum.class);
        for (Object[] row : byDiffRaw) {
            LevelEnum diff = (LevelEnum) row[0];
            Long cnt = ((Number) row[1]).longValue();
            byDifficulty.put(diff, cnt);
        }

        return new CrosswordSummaryResponse(
                totalStarted, totalCompleted, avgTime,
                wordsFoundTotal, wordsTotalTotal, mistakesTotal,
                byDifficulty
        );
    }

    private int nz(Integer n) { return n == null ? 0 : n; }
}
