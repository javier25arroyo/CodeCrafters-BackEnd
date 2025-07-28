package com.project.demo.service;

import com.project.demo.logic.entity.settings.UserSettings;

/**
 * Interfaz para el servicio de gestión de la configuración de usuario.
 * Define las operaciones para actualizar y obtener la configuración de usuario.
 */
public interface UserSettingsService {
    /**
     * Actualiza la configuración de usuario para un usuario específico.
     *
     * @param userId   El ID del usuario cuya configuración se va a actualizar.
     * @param settings El objeto {@link UserSettings} con los datos actualizados.
     * @return La configuración de usuario actualizada.
     */
    UserSettings updateUserSettings(Long userId, UserSettings settings);

    /**
     * Obtiene la configuración de usuario para un usuario específico.
     *
     * @param userId El ID del usuario cuya configuración se va a obtener.
     * @return La configuración de usuario.
     */
    UserSettings getUserSettings(Long userId);
}