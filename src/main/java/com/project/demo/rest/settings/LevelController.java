package com.project.demo.rest.settings;

import com.project.demo.logic.entity.settings.Level;
import com.project.demo.logic.entity.settings.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/levels")
public class LevelController {

    @Autowired
    private LevelRepository levelRepository;

    @GetMapping
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    @PostMapping
    public Level createLevel(@RequestBody Level level) {
        return levelRepository.save(level);
    }
}