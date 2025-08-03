package com.project.demo.logic.entity.timeline;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimelineEventRepository extends JpaRepository<TimelineEvent, Long> {
    List<TimelineEvent> findAllByDifficulty(Difficulty difficulty);
}
