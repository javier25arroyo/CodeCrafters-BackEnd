package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.Score;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.settings.LevelEnum; // <- usa tu enum de niveles
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    long countByUserId(Long userId);
    long countByUserIdAndGameType(Long userId, GameTypeEnum gameType);

    boolean existsByUserIdAndTimeLessThanEqual(Long userId, long seconds);
    boolean existsByUserIdAndMovementsLessThanEqual(Long userId, int moves);

    // FIX: para enums no aplica IgnoreCase; compara directamente por LevelEnum
    boolean existsByUserIdAndLevel(Long userId, LevelEnum level);
}
