// src/main/java/com/project/demo/logic/entity/timeline/TimelineEventSeeder.java
package com.project.demo.logic.entity.timeline;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TimelineEventSeeder implements CommandLineRunner {
    private final TimelineEventRepository repo;

    public TimelineEventSeeder(TimelineEventRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        if (repo.count() > 0) return;

        // EASY: meses
        for (int m = 1; m <= 12; m++) {
            TimelineEvent e = new TimelineEvent();
            e.setTitle("Mes " + m);
            e.setDescription("Ordena el mes " + m);
            e.setYear(m);
            e.setDifficulty(Difficulty.EASY);
            repo.save(e);
        }

        // MEDIUM: Costa Rica
        List<TimelineEvent> cr = List.of(
                ev("Independencia de Costa Rica", "…", 1821),
                ev("Anexión de Guanacaste",       "…", 1824),
                ev("Separación de la República",  "…", 1838),
                ev("Batalla de Santa Rosa",       "…", 1856),
                ev("Abolición del Ejército",      "…", 1948)
        );
        cr.forEach(e -> { e.setDifficulty(Difficulty.MEDIUM); repo.save(e); });

        // HARD: mundiales
        List<TimelineEvent> wd = List.of(
                ev("Revolución Francesa",        "…", 1789),
                ev("Descubrimiento de América",  "…", 1492),
                ev("Primera Guerra Mundial",     "…", 1914),
                ev("Llegada a la Luna",          "…", 1969),
                ev("Caída del Muro de Berlín",   "…", 1989)
        );
        wd.forEach(e -> { e.setDifficulty(Difficulty.HARD); repo.save(e); });
    }

    private TimelineEvent ev(String title, String desc, int year) {
        TimelineEvent e = new TimelineEvent();
        e.setTitle(title);
        e.setDescription(desc);
        e.setYear(year);
        return e;
    }
}
