package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.Score;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Score.
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {
}
