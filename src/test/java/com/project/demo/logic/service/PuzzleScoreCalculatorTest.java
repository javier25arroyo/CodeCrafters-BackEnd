package com.project.demo.logic.service;

import com.project.demo.logic.entity.settings.LevelEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el calculador de puntuaciones de puzzles.
 */
class PuzzleScoreCalculatorTest {

    private PuzzleScoreCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new PuzzleScoreCalculator();
    }

    @Test
    void testEasyLevelOptimalScore() {
        // Caso óptimo: 40 seg, 8 movimientos = 1000 puntos
        double score = calculator.calculateScore(LevelEnum.EASY, 40, 8);
        assertEquals(1000.0, score, 0.01);
    }

    @Test
    void testEasyLevelTimeExtraPenalty() {
        // 50 seg, 8 movimientos: pierdes 10×5=50 → 950 puntos
        double score = calculator.calculateScore(LevelEnum.EASY, 50, 8);
        assertEquals(950.0, score, 0.01);
    }

    @Test
    void testEasyLevelMovementExtraPenalty() {
        // 40 seg, 10 movimientos: pierdes 2×20=40 → 960 puntos
        double score = calculator.calculateScore(LevelEnum.EASY, 40, 10);
        assertEquals(960.0, score, 0.01);
    }

    @Test
    void testEasyLevelBothPenalties() {
        // 50 seg, 10 movimientos: pierdes 50+40=90 → 910 puntos  
        double score = calculator.calculateScore(LevelEnum.EASY, 50, 10);
        assertEquals(910.0, score, 0.01);
    }

    @Test
    void testMediumLevelOptimalScore() {
        // Caso óptimo: 80 seg, 17 movimientos = 2500 puntos
        double score = calculator.calculateScore(LevelEnum.MEDIUM, 80, 17);
        assertEquals(2500.0, score, 0.01);
    }

    @Test
    void testMediumLevelTimeExtraPenalty() {
        // 90 seg, 17 movimientos: pierdes 10×6=60 → 2440 puntos
        double score = calculator.calculateScore(LevelEnum.MEDIUM, 90, 17);
        assertEquals(2440.0, score, 0.01);
    }

    @Test
    void testMediumLevelMovementExtraPenalty() {
        // 80 seg, 20 movimientos: pierdes 3×25=75 → 2425 puntos
        double score = calculator.calculateScore(LevelEnum.MEDIUM, 80, 20);
        assertEquals(2425.0, score, 0.01);
    }

    @Test
    void testHardLevelOptimalScore() {
        // Caso óptimo: 120 seg, 26 movimientos = 5000 puntos
        double score = calculator.calculateScore(LevelEnum.HARD, 120, 26);
        assertEquals(5000.0, score, 0.01);
    }

    @Test
    void testHardLevelTimeExtraPenalty() {
        // 140 seg, 26 movimientos: pierdes 20×8=160 → 4840 puntos
        double score = calculator.calculateScore(LevelEnum.HARD, 140, 26);
        assertEquals(4840.0, score, 0.01);
    }

    @Test
    void testHardLevelMovementExtraPenalty() {
        // 120 seg, 30 movimientos: pierdes 4×30=120 → 4880 puntos
        double score = calculator.calculateScore(LevelEnum.HARD, 120, 30);
        assertEquals(4880.0, score, 0.01);
    }

    @Test
    void testScoreNeverGoesNegative() {
        // Caso extremo: muchas penalizaciones
        double score = calculator.calculateScore(LevelEnum.EASY, 200, 50);
        assertTrue(score >= 0, "La puntuación no debe ser negativa");
    }

    @Test
    void testBetterThanOptimalPerformance() {
        // Rendimiento mejor que el óptimo no debería dar puntos extra
        double score = calculator.calculateScore(LevelEnum.EASY, 30, 6);
        assertEquals(1000.0, score, 0.01);
    }
}