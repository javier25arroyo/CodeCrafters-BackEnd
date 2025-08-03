package com.project.demo.rest.timeline;

import com.project.demo.logic.entity.timeline.Difficulty;
import com.project.demo.logic.entity.timeline.TimelineEvent;
import com.project.demo.service.TimelineEventService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/timeline")
@CrossOrigin(origins = "http://localhost:4200")
public class TimelineEventController {
    private final TimelineEventService repo;

    public TimelineEventController(TimelineEventService repo) {
        this.repo = repo;
    }

    /**
     * GET /api/timeline/events?difficulty=MEDIUM
     * Devuelve la lista de eventos para la dificultad indicada.
     */
    @GetMapping
    public List<TimelineEvent> getByDifficulty(@RequestParam("difficulty") Difficulty difficulty) {
        return repo.findAllByDifficulty(difficulty);
    }
}
