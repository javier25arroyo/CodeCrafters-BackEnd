package com.project.demo.logic.entity.game;

import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.settings.LevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Seeder para inicializar los juegos en la base de datos.
 * Se ejecuta al iniciar la aplicación y crea los registros necesarios
 * para todos los tipos de juego disponibles.
 */
@Component
@Order(3)
@org.springframework.context.annotation.Profile("!test")
public class GameSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Método que se ejecuta al iniciar la aplicación.
     * Este método se encarga de sembrar los juegos iniciales en la base de datos
     * @param contextRefreshedEvent el evento que indica que el contexto de la aplicación ha sido inicializado o refrescado
     */

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.seedGames();
    }

    /**
     * Crea los registros iniciales de juegos para todos los tipos disponibles.
     */
    private void seedGames() {
        for (GameTypeEnum gameType : GameTypeEnum.values()) {
            if (!gameRepository.findFirstByGameType(gameType).isPresent()) {
                Game game = new Game();
                game.setGameType(gameType);

                // Asignar dificultad solo a MUSIC_MEMORY
                if (gameType == GameTypeEnum.MUSIC_MEMORY) {
                    game.setLevel(LevelEnum.FACIL);
                } else {
                    game.setLevel(null);
                }

                gameRepository.save(game);
        }
    }
}
}