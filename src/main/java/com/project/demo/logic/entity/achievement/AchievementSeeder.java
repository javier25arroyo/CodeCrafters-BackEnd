package com.project.demo.logic.entity.achievement;

import com.project.demo.logic.entity.achievement.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Inserta logros base al iniciar la app (si no existen).
 * Usa 'name' como CLAVE (first_win, five_wins, ...).
 */
@Component
@Order(4)        // después del GameSeeder (que está en @Order(3))
@Profile("!test")
public class AchievementSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AchievementRepository achievementRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        seedAchievements();
    }

    private void seedAchievements() {
        List<Seed> seeds = List.of(
                // name (clave), description (breve)
                new Seed("first_win", "Completa tu primera partida."),
                new Seed("five_wins", "Completa 5 partidas en total."),
                new Seed("puzzle_3", "Completa 3 partidas de Rompecabezas."),
                new Seed("ws_3", "Completa 3 partidas de Sopa de Letras."),
                new Seed("seq_3", "Completa 3 partidas de Secuencia."),
                new Seed("cross_3", "Completa 3 partidas de Crucigrama."),
                new Seed("under_60", "Termina una partida en 60s o menos."),
                new Seed("few_moves", "Termina una partida con ≤ 10 movimientos."),
                new Seed("hard_win", "Gana en dificultad HARD.")
        );

        for (Seed s : seeds) {
            achievementRepository.findByName(s.name()).orElseGet(() -> {
                Achievement a = new Achievement();
                a.setName(s.name());           // CLAVE
                a.setDescription(s.description());
                return achievementRepository.save(a);
            });
        }
    }

    private record Seed(String name, String description) {}
}
