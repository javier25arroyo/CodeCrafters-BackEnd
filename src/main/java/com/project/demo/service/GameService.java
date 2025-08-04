package com.project.demo.service;

import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameReport;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.repository.GameReportRepository;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.game.games.PuzzleResultDTO;
import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GameService {

    @Autowired
    private GameReportRepository gameReportRepository;

    @Autowired
    private GameRepository gameRepository;

    public GameReport savePuzzleResult(User user, PuzzleResultDTO puzzleResult) {
        Game puzzleGame = gameRepository.findByGameType(GameTypeEnum.PUZZLE).orElseThrow(() -> new RuntimeException("Game not found"));

        int score = calculateScore(puzzleResult.getTime(), puzzleResult.getMovements(), puzzleResult.getDifficulty());

        GameReport report = new GameReport();
        report.setUser(user);
        report.setGame(puzzleGame);
        report.setDuration(puzzleResult.getTime());
        report.setScore(score);
        report.setResult("Completed");
        report.setDate(LocalDateTime.now());

        return gameReportRepository.save(report);
    }

    private int calculateScore(int timeInSeconds, int movements, LevelEnum difficulty) {
        int score = 0;
        switch (difficulty) {
            case EASY:
                score = 1000 - ((timeInSeconds - 40) * 10) - ((movements - 8) * 20);
                break;
            case MEDIUM:
                score = 2500 - ((timeInSeconds - 80) * 6) - ((movements - 17) * 25);
                break;
            case HARD:
                score = 5000 - ((timeInSeconds - 120) * 8) - ((movements - 26) * 30);
                break;
        }
        return Math.max(0, score);
    }
}
