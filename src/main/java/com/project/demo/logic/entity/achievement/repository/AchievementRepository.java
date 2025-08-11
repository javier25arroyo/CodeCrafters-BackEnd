package com.project.demo.logic.entity.achievement.repository;

import com.project.demo.logic.entity.achievement.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {
    /**
     * Busca un logro por su nombre (clave única, por ejemplo: "first_win").
     */
    Optional<Achievement> findByName(String name);
}
