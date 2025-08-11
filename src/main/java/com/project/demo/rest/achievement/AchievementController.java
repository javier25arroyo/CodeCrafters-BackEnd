package com.project.demo.rest.achievement;

import com.project.demo.logic.entity.achievement.Achievement;
import com.project.demo.logic.entity.achievement.UserAchievement;
import com.project.demo.logic.entity.achievement.repository.AchievementRepository;
import com.project.demo.logic.entity.achievement.repository.UserAchievementRepository;
import com.project.demo.logic.entity.achievement.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping("/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    /**
     * Recalcula/otorga logros y retorna TODOS los logros indicando
     * cuáles tiene el usuario y cuándo los desbloqueó.
     */
    @GetMapping("/user/{userId}")
    public List<UserAchievementDTO> listForUser(@PathVariable Long userId) {
        // 1) Otorga nuevos si corresponde (no borra los ya otorgados)
        achievementService.recomputeAndPersist(userId);

        // 2) Cargar todos los achievements
        List<Achievement> all = achievementRepository.findAll();

        // 3) Cargar los del usuario
        List<UserAchievement> uaList = userAchievementRepository.findByUserId(userId);
        Map<Integer, UserAchievement> uaByAchId = new HashMap<>();
        for (UserAchievement ua : uaList) {
            if (ua.getAchievement() != null) {
                uaByAchId.put(ua.getAchievement().getId(), ua);
            }
        }

        // 4) Construir respuesta
        List<UserAchievementDTO> out = new ArrayList<>(all.size());
        for (Achievement a : all) {
            UserAchievement ua = uaByAchId.get(a.getId());
            out.add(UserAchievementDTO.of(a, ua));
        }
        // Orden opcional por nombre de clave
        out.sort(Comparator.comparing(UserAchievementDTO::getName));
        return out;
    }

    // DTO de respuesta
    public static class UserAchievementDTO {
        private Integer id;
        private String name;         // clave: first_win, ...
        private String description;  // breve
        private boolean unlocked;
        private Date dateUnlocked;   // nullable

        public static UserAchievementDTO of(Achievement a, UserAchievement ua) {
            UserAchievementDTO dto = new UserAchievementDTO();
            dto.id = a.getId();
            dto.name = a.getName();
            dto.description = a.getDescription();
            dto.unlocked = (ua != null);
            if (ua != null && ua.getDate() != null) {
                dto.dateUnlocked = Date.from(
                        ua.getDate().atZone(ZoneId.systemDefault()).toInstant()
                );
            }
            return dto;
        }

        public Integer getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public boolean isUnlocked() { return unlocked; }
        public Date getDateUnlocked() { return dateUnlocked; }

        public void setId(Integer id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setDescription(String description) { this.description = description; }
        public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
        public void setDateUnlocked(Date dateUnlocked) { this.dateUnlocked = dateUnlocked; }
    }
}
