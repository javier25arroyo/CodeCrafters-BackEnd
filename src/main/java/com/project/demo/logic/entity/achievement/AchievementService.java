package com.project.demo.logic.entity.achievement;

import com.project.demo.logic.entity.achievement.Achievement;
import com.project.demo.logic.entity.achievement.UserAchievement;
import com.project.demo.logic.entity.achievement.repository.AchievementRepository;
import com.project.demo.logic.entity.achievement.repository.UserAchievementRepository;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.repository.ScoreRepository;
import com.project.demo.logic.entity.settings.LevelEnum;
import com.project.demo.logic.entity.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AchievementService {

    private final ScoreRepository scoreRepo;
    private final AchievementRepository achRepo;
    private final UserAchievementRepository userAchRepo;

    public AchievementService(
            ScoreRepository scoreRepo,
            AchievementRepository achRepo,
            UserAchievementRepository userAchRepo
    ) {
        this.scoreRepo = scoreRepo;
        this.achRepo = achRepo;
        this.userAchRepo = userAchRepo;
    }

    /**
     * Recalcula los logros para el usuario dado y persiste los que
     * correspondan (no elimina logros ya otorgados).
     * Devuelve un mapa clave->desbloqueado.
     */
    @Transactional
    public Map<String, Boolean> recomputeAndPersist(Long userId) {
        long total   = scoreRepo.countByUserId(userId);
        long puzzle3 = scoreRepo.countByUserIdAndGameType(userId, GameTypeEnum.PUZZLE);
        long ws3     = scoreRepo.countByUserIdAndGameType(userId, GameTypeEnum.WORD_SEARCH);
        long seq3    = scoreRepo.countByUserIdAndGameType(userId, GameTypeEnum.NUMBER_SEQUENCE);
        long cross3  = scoreRepo.countByUserIdAndGameType(userId, GameTypeEnum.CROSSWORD);

        boolean under60  = scoreRepo.existsByUserIdAndTimeLessThanEqual(userId, 60);
        boolean fewMoves = scoreRepo.existsByUserIdAndMovementsLessThanEqual(userId, 10);
        boolean hard     = scoreRepo.existsByUserIdAndLevel(userId, LevelEnum.HARD); // <- enum

        Map<String, Boolean> unlocked = new LinkedHashMap<>();
        unlocked.put("first_win", total >= 1);
        unlocked.put("five_wins", total >= 5);
        unlocked.put("puzzle_3", puzzle3 >= 3);
        unlocked.put("ws_3", ws3 >= 3);
        unlocked.put("seq_3", seq3 >= 3);
        unlocked.put("cross_3", cross3 >= 3);
        unlocked.put("under_60", under60);
        unlocked.put("few_moves", fewMoves);
        unlocked.put("hard_win", hard);

        // Crear relación en user_achievements si falta
        LocalDateTime now = LocalDateTime.now();
        unlocked.forEach((key, ok) -> {
            if (!ok) return;

            achRepo.findByName(key).ifPresent(ach -> {
                if (!userAchRepo.existsByUserIdAndAchievementId(userId, ach.getId())) {
                    UserAchievement ua = new UserAchievement();

                    // Evita un fetch del usuario: setea solo el id
                    User u = new User();
                    u.setId(userId);
                    ua.setUser(u);

                    ua.setAchievement(ach);
                    ua.setDate(now);
                    userAchRepo.save(ua);
                }
            });
        });

        return unlocked;
    }
}
