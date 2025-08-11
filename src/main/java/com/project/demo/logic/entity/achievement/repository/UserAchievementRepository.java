package com.project.demo.logic.entity.achievement.repository;

import com.project.demo.logic.entity.achievement.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Integer> {
    /**
     * Verifica si un usuario ya tiene un logro específico.
     */
    boolean existsByUserIdAndAchievementId(Long userId, Integer achievementId);

    /**
     * Lista todos los logros que tiene un usuario.
     */
    List<UserAchievement> findByUserId(Long userId);
}
