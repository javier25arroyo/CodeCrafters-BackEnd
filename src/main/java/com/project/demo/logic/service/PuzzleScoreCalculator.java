package com.project.demo.logic.service;

import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.stereotype.Service;

/**
 * Servicio para calcular las puntuaciones de los puzzles basado en tiempo y movimientos.
 */
@Service
public class PuzzleScoreCalculator {

    /**
     * Calcula la puntuación de un puzzle basado en el nivel, tiempo y movimientos.
     * 
     * @param level Nivel del puzzle (EASY, MEDIUM, HARD)
     * @param timeInSeconds Tiempo tomado en segundos
     * @param movements Número de movimientos realizados
     * @return Puntuación calculada
     */
    public double calculateScore(LevelEnum level, long timeInSeconds, int movements) {
        switch (level) {
            case EASY:
                return calculateEasyScore(timeInSeconds, movements);
            case MEDIUM:
                return calculateMediumScore(timeInSeconds, movements);
            case HARD:
                return calculateHardScore(timeInSeconds, movements);
            default:
                throw new IllegalArgumentException("Nivel no soportado: " + level);
        }
    }

    /**
     * Calcula la puntuación para nivel FÁCIL (3x3).
     * Base: 1000 puntos por resolver en 40 seg y 8 movimientos
     * Penalización tiempo: 10 puntos por cada segundo extra
     * Penalización movimientos: 20 puntos por cada movimiento extra
     */
    private double calculateEasyScore(long timeInSeconds, int movements) {
        final int BASE_SCORE = 1000;
        final int OPTIMAL_TIME = 40;
        final int OPTIMAL_MOVEMENTS = 8;
        final int TIME_PENALTY = 10;
        final int MOVEMENT_PENALTY = 20;

        double score = BASE_SCORE;

        // Penalización por tiempo extra
        if (timeInSeconds > OPTIMAL_TIME) {
            score -= (timeInSeconds - OPTIMAL_TIME) * TIME_PENALTY;
        }

        // Penalización por movimientos extra
        if (movements > OPTIMAL_MOVEMENTS) {
            score -= (movements - OPTIMAL_MOVEMENTS) * MOVEMENT_PENALTY;
        }

        return Math.max(0, score);
    }

    /**
     * Calcula la puntuación para nivel MEDIO (4x4).
     * Base: 2500 puntos por resolver en 80 seg y 17 movimientos
     * Penalización tiempo: 6 puntos por cada segundo extra
     * Penalización movimientos: 25 puntos por cada movimiento extra
     */
    private double calculateMediumScore(long timeInSeconds, int movements) {
        final int BASE_SCORE = 2500;
        final int OPTIMAL_TIME = 80;
        final int OPTIMAL_MOVEMENTS = 17;
        final int TIME_PENALTY = 6;
        final int MOVEMENT_PENALTY = 25;

        double score = BASE_SCORE;

        // Penalización por tiempo extra
        if (timeInSeconds > OPTIMAL_TIME) {
            score -= (timeInSeconds - OPTIMAL_TIME) * TIME_PENALTY;
        }

        // Penalización por movimientos extra
        if (movements > OPTIMAL_MOVEMENTS) {
            score -= (movements - OPTIMAL_MOVEMENTS) * MOVEMENT_PENALTY;
        }

        return Math.max(0, score);
    }

    /**
     * Calcula la puntuación para nivel DIFÍCIL (5x5).
     * Base: 5000 puntos por resolver en 120 seg y 26 movimientos
     * Penalización tiempo: 8 puntos por cada segundo extra
     * Penalización movimientos: 30 puntos por cada movimiento extra
     */
    private double calculateHardScore(long timeInSeconds, int movements) {
        final int BASE_SCORE = 5000;
        final int OPTIMAL_TIME = 120;
        final int OPTIMAL_MOVEMENTS = 26;
        final int TIME_PENALTY = 8;
        final int MOVEMENT_PENALTY = 30;

        double score = BASE_SCORE;

        // Penalización por tiempo extra
        if (timeInSeconds > OPTIMAL_TIME) {
            score -= (timeInSeconds - OPTIMAL_TIME) * TIME_PENALTY;
        }

        // Penalización por movimientos extra
        if (movements > OPTIMAL_MOVEMENTS) {
            score -= (movements - OPTIMAL_MOVEMENTS) * MOVEMENT_PENALTY;
        }

        return Math.max(0, score);
    }
}