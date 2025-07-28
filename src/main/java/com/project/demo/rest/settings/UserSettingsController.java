package com.project.demo.rest.settings;

import com.project.demo.logic.entity.settings.UserSettings;
import com.project.demo.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class UserSettingsController {

    @Autowired
    private UserSettingsService userSettingsService;

    /**
     * Constructor por defecto.
     */
    public UserSettingsController() {
    }

    /**
     * Actualiza la configuración de usuario para un usuario específico.
     *
     * @param userId   El ID del usuario cuya configuración se va a actualizar.
     * @param settings El objeto {@link UserSettings} con los datos actualizados.
     * @return ResponseEntity con la configuración de usuario actualizada.
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public ResponseEntity<UserSettings> updateUserSettings(@PathVariable Long userId, @RequestBody UserSettings settings) {
        return ResponseEntity.ok(userSettingsService.updateUserSettings(userId, settings));
    }

    /**
     * Obtiene la configuración de usuario para un usuario específico.
     *
     * @param userId El ID del usuario cuya configuración se va a obtener.
     * @return ResponseEntity con la configuración de usuario.
     */
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    public ResponseEntity<UserSettings> getUserSettings(@PathVariable Long userId) {
        return ResponseEntity.ok(userSettingsService.getUserSettings(userId));
    }
}
