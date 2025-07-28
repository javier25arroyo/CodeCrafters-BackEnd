package com.project.demo.service;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el servicio de gestión de usuarios.
 * Define las operaciones CRUD y otras funcionalidades relacionadas con los usuarios.
 */
public interface UserService {
    /**
     * Obtiene una página de todos los usuarios.
     *
     * @param pageable Información de paginación.
     * @return Una página de usuarios.
     */
    Page<User> getAllUsers(Pageable pageable);

    /**
     * Crea un nuevo usuario.
     *
     * @param user El usuario a crear.
     * @return El usuario creado.
     */
    User createUser(User user);

    /**
     * Actualiza un usuario existente.
     *
     * @param userId El ID del usuario a actualizar.
     * @param user   Los datos del usuario actualizado.
     * @return Un Optional que contiene el usuario actualizado si existe, o vacío si no.
     */
    Optional<User> updateUser(Long userId, User user);

    /**
     * Elimina un usuario por su ID.
     *
     * @param userId El ID del usuario a eliminar.
     * @return True si el usuario fue eliminado, false en caso contrario.
     */
    boolean deleteUser(Long userId);

    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return El usuario autenticado.
     */
    User getCurrentUser();

    /**
     * Restablece la contraseña de un usuario a un valor predeterminado.
     *
     * @param userId El ID del usuario cuya contraseña se va a restablecer.
     * @return True si la contraseña fue restablecida, false en caso contrario.
     */
    boolean resetPassword(Long userId);

    /**
     * Restablece la contraseña de un usuario a un valor predeterminado y devuelve el usuario actualizado.
     *
     * @param userId El ID del usuario cuya contraseña se va a restablecer.
     * @return Un Optional que contiene el usuario actualizado si existe, o vacío si no.
     */
    Optional<User> resetPasswordAndGetUser(Long userId);

    /**
     * Obtiene un resumen de todos los usuarios.
     *
     * @return Una lista de resúmenes de usuarios.
     */
    List<UserSummary> getUserSummary();

    /**
     * Alterna el estado de habilitación de un usuario (enabled/disabled).
     *
     * @param userId El ID del usuario cuyo estado se va a alternar.
     * @return Un Optional que contiene el usuario actualizado si existe, o vacío si no.
     */
    Optional<User> toggleUserEnabled(Long userId);

    /**
     * Busca un usuario por su ID.
     *
     * @param userId El ID del usuario a buscar.
     * @return Un Optional que contiene el usuario si existe, o vacío si no.
     */
    Optional<User> findUserById(Long userId);
}