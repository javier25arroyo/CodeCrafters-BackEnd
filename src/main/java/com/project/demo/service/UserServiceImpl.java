package com.project.demo.service;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de usuarios.
 * Proporciona la lógica de negocio para las operaciones relacionadas con los usuarios.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    /**
     * Constructor por defecto.
     */
    public UserServiceImpl() {
    }

    /**
     * Obtiene una página de todos los usuarios.
     *
     * @param pageable Información de paginación.
     * @return Una página de usuarios.
     */
    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param user El usuario a crear.
     * @return El usuario creado.
     */
    @Override
    public User createUser(User user) {
        user.setPassword(passwordService.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param userId El ID del usuario a actualizar.
     * @param user   Los datos del usuario actualizado.
     * @return Un Optional que contiene el usuario actualizado si existe, o vacío si no.
     */
    @Override
    public Optional<User> updateUser(Long userId, User user) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());

                    if (user.getRole() != null) {
                        existingUser.setRole(user.getRole());
                    }

                    if (user.getPassword() != null && !user.getPassword().isBlank()) {
                        existingUser.setPassword(passwordService.encode(user.getPassword()));
                    }

                    return userRepository.save(existingUser);
                });
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param userId El ID del usuario a eliminar.
     * @return True si el usuario fue eliminado, false en caso contrario.
     */
    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return El usuario autenticado.
     */
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    /**
     * Restablece la contraseña de un usuario a un valor predeterminado.
     *
     * @param userId El ID del usuario cuya contraseña se va a restablecer.
     * @return True si la contraseña fue restablecida, false en caso contrario.
     */
    @Override
    public boolean resetPassword(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String defaultPassword = passwordService.generateDefaultPassword();
            user.setPassword(passwordService.encode(defaultPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /**
     * Restablece la contraseña de un usuario a un valor predeterminado y devuelve el usuario actualizado.
     *
     * @param userId El ID del usuario cuya contraseña se va a restablecer.
     * @return Un Optional que contiene el usuario actualizado si existe, o vacío si no.
     */
    public Optional<User> resetPasswordAndGetUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    String defaultPassword = passwordService.generateDefaultPassword();
                    user.setPassword(passwordService.encode(defaultPassword));
                    return userRepository.save(user);
                });
    }

    /**
     * Obtiene un resumen de todos los usuarios.
     *
     * @return Una lista de resúmenes de usuarios.
     */
    @Override
    public List<UserSummary> getUserSummary() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserSummary(
                        user.getName(),
                        user.getEmail(),
                        user.getEnabled(),
                        user.getRole().getName().toString()))
                .collect(Collectors.toList());
    }

    /**
     * Alterna el estado de habilitación de un usuario (enabled/disabled).
     *
     * @param userId El ID del usuario cuyo estado se va a alternar.
     * @return Un Optional que contiene el usuario actualizado si existe, o vacío si no.
     */
    @Override
    public Optional<User> toggleUserEnabled(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setEnabled(!user.getEnabled());
                    return userRepository.save(user);
                });
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param userId El ID del usuario a buscar.
     * @return Un Optional que contiene el usuario si existe, o vacío si no.
     */
    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}