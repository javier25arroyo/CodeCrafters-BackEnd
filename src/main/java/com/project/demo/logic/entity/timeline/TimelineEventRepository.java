package com.project.demo.logic.entity.timeline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TimelineEventRepository
        extends JpaRepository<TimelineEvent, Long> {

    /** Devuelve todos los eventos que tienen el nivel de dificultad indicado */
    List<TimelineEvent> findAllByDifficulty(Difficulty difficulty);
}
