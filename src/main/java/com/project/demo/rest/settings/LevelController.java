package com.project.demo.rest.settings;

import com.project.demo.logic.entity.settings.Level;
import com.project.demo.logic.entity.settings.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar los niveles del sistema.
 * Proporciona endpoints para obtener y crear niveles.
 */
@RestController
@RequestMapping("/levels")
public class LevelController {

    @Autowired
    private LevelRepository levelRepository;

    /**
     * Obtiene todos los niveles disponibles en el sistema.
     *
     * @return Una lista de todos los objetos {@link Level}.
     */
    @GetMapping
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    /**
     * Crea un nuevo nivel en el sistema.
     *
     * @param level El objeto {@link Level} a crear.
     * @return El nivel guardado.
     */
    @PostMapping
    public Level createLevel(@RequestBody Level level) {
        return levelRepository.save(level);
    }
}