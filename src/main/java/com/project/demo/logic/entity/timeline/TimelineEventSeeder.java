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
    public void run(String... args) throws Exception {
        if (repo.count() > 0) return;

        // EASY: meses
        for (int m = 1; m <= 12; m++) {
            TimelineEvent ev = new TimelineEvent();
            ev.setTitle("Mes " + m);
            ev.setDescription("Ordena el mes " + m);
            ev.setYear(m);
            ev.setDifficulty(Difficulty.EASY);
            repo.save(ev);
        }

        // MEDIUM: Costa Rica
        List<TimelineEvent> cr = List.of(
                ev( "Independencia de Costa Rica",       "…", 1821),
                ev( "Anexión de Guanacaste",             "…", 1824),
                ev( "Separación de la República",        "…", 1838),
                ev( "Batalla de Santa Rosa",             "…", 1856),
                ev( "Abolición del Ejército",            "…", 1948)
        );
        cr.forEach(e->{ e.setDifficulty(Difficulty.MEDIUM); repo.save(e); });

        // HARD: ejemplos mundiales
        List<TimelineEvent> wd = List.of(
                ev("Revolución Francesa","…",1789),
                ev("Descubrimiento de América","…",1492),
                ev("Primera Guerra Mundial","…",1914),
                ev("Caída del Muro de Berlín","…",1989),
                ev("Llegada a la Luna","…",1969)
        );
        wd.forEach(e->{ e.setDifficulty(Difficulty.HARD); repo.save(e); });
    }

    private TimelineEvent ev(String title, String desc, int year) {
        TimelineEvent e = new TimelineEvent();
        e.setTitle(title); e.setDescription(desc); e.setYear(year);
        return e;
    }
}
