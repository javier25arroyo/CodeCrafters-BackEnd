package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.GameScoreStat;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.Score;
import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    long countByUserId(Long userId);

    long countByUserIdAndGameType(Long userId, GameTypeEnum gameType);

    boolean existsByUserIdAndTimeLessThanEqual(Long userId, long seconds);

    boolean existsByUserIdAndMovementsLessThanEqual(Long userId, int moves);

    // FIX: para enums no aplica IgnoreCase; compara directamente por LevelEnum
    boolean existsByUserIdAndLevel(Long userId, LevelEnum level);

    Optional<Score> findByUserIdAndGameTypeAndLevel(Long userId, GameTypeEnum gameType, LevelEnum level);

    @Query("SELECT new com.project.demo.logic.entity.game.GameScoreStat(s.gameType, SUM(s.score)) " +
            "FROM Score s WHERE s.user.id = :userId GROUP BY s.gameType")
    List<GameScoreStat> findMaxScoreByGameTypeAndUserId(@Param("userId") Long userId);

}
