package com.project.demo.logic.entity.settings.repository;

import com.project.demo.logic.entity.settings.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
}