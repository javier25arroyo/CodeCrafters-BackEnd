package com.project.demo.logic.entity.game.repository;

import com.project.demo.logic.entity.game.CrosswordStat;
import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CrosswordStatRepository extends JpaRepository<CrosswordStat, Integer> {

    long countByStartedAtBetween(LocalDateTime from, LocalDateTime to);

    long countByCompletedIsTrueAndStartedAtBetween(LocalDateTime from, LocalDateTime to);

    @Query("select coalesce(avg(c.timeSeconds), 0) from CrosswordStat c where c.startedAt between :from and :to")
    Double avgTimeSecondsBetween(LocalDateTime from, LocalDateTime to);

    @Query("""
        select c.difficulty as difficulty, count(c) as total 
        from CrosswordStat c 
        where c.startedAt between :from and :to 
        group by c.difficulty
    """)
    List<Object[]> countByDifficultyBetween(LocalDateTime from, LocalDateTime to);

    @Query("""
        select coalesce(sum(c.wordsFound),0), coalesce(sum(c.wordsTotal),0), coalesce(sum(c.mistakes),0)
        from CrosswordStat c
        where c.startedAt between :from and :to
    """)
    Object[] totalsBetween(LocalDateTime from, LocalDateTime to);
}
