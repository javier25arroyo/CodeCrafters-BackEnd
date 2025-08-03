package com.project.demo.service;

import com.project.demo.logic.entity.timeline.Difficulty;
import com.project.demo.logic.entity.timeline.TimelineEvent;
import com.project.demo.logic.entity.timeline.TimelineEventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TimelineEventService {
    private final TimelineEventRepository repo;

    public TimelineEventService(TimelineEventRepository repo) {
        this.repo = repo;
    }

    /** Devuelve todos los eventos de la dificultad indicada */
    public List<TimelineEvent> findAllByDifficulty(Difficulty difficulty) {
        return repo.findAllByDifficulty(difficulty);
    }
}
