package com.project.demo.rest.timeline;

import com.project.demo.logic.entity.timeline.Difficulty;
import com.project.demo.logic.entity.timeline.TimelineEvent;
import com.project.demo.logic.entity.timeline.TimelineEventRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/timeline")
public class TimelineEventController {
    private final TimelineEventRepository repo;

    public TimelineEventController(TimelineEventRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<TimelineEvent> getByDifficulty(@RequestParam("difficulty") Difficulty difficulty) {
        return repo.findAllByDifficulty(difficulty);
    }
}