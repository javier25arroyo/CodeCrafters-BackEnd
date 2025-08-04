package com.project.demo.rest.game;

import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los juegos.
 * Proporciona endpoints para operaciones relacionadas con la gestión de juegos.
 */
@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {
    
    @Autowired
    private GameRepository gameRepository;

    /**
     * Constructor por defecto.
     */
    public GameController() {
    }
}
