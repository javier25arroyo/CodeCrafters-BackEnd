package com.project.demo.rest.user;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.http.Meta;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.user.UserSummary;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para operaciones CRUD sobre usuarios y para obtener el usuario autenticado.
 */
@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Obtiene una lista paginada de todos los usuarios.
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param page    Número de página (por defecto 1).
     * @param size    Tamaño de la página (por defecto 10).
     * @param request La petición HTTP.
     * @return ResponseEntity con la lista de usuarios y metadatos de paginación.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<User> ordersPage = userRepository.findAll(pageable);
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        meta.setTotalPages(ordersPage.getTotalPages());
        meta.setTotalElements(ordersPage.getTotalElements());
        meta.setPageNumber(ordersPage.getNumber() + 1);
        meta.setPageSize(ordersPage.getSize());

        return new GlobalResponseHandler().handleResponse("Users retrieved successfully",
                ordersPage.getContent(), HttpStatus.OK, meta);
    }

    /**
     * Agrega un nuevo usuario al sistema.
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param user    El objeto {@link User} a agregar.
     * @param request La petición HTTP.
     * @return ResponseEntity con el usuario agregado y un mensaje de éxito.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new GlobalResponseHandler().handleResponse("User updated successfully",
                user, HttpStatus.OK, request);
    }

    /**
     * Actualiza un usuario existente por su ID.
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param userId  El ID del usuario a actualizar.
     * @param user    El objeto {@link User} con los datos actualizados.
     * @param request La petición HTTP.
     * @return ResponseEntity con el usuario actualizado o un mensaje de error si no se encuentra.
     */
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user, HttpServletRequest request) {
        Optional<User> foundOrder = userRepository.findById(userId);
        if(foundOrder.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new GlobalResponseHandler().handleResponse("User updated successfully",
                    user, HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("User id " + userId + " not found"  ,
                    HttpStatus.NOT_FOUND, request);
        }
    }


    /**
     * Elimina un usuario por su ID.
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param userId  El ID del usuario a eliminar.
     * @param request La petición HTTP.
     * @return ResponseEntity con el usuario eliminado o un mensaje de error si no se encuentra.
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, HttpServletRequest request) {
        Optional<User> foundOrder = userRepository.findById(userId);
        if(foundOrder.isPresent()) {
            userRepository.deleteById(userId);
            return new GlobalResponseHandler().handleResponse("User deleted successfully",
                    foundOrder.get(), HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("Order id " + userId + " not found"  ,
                    HttpStatus.NOT_FOUND, request);
        }
    }

    /**
     * Obtiene la información del usuario autenticado actualmente.
     * Requiere que el usuario esté autenticado.
     *
     * @return El objeto {@link User} del usuario autenticado.
     */
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN', 'SUPER_ADMIN')")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    /**
     * Restablece la contraseña de un usuario a un valor predeterminado.
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param userId  El ID del usuario cuya contraseña se va a restablecer.
     * @param request La petición HTTP.
     * @return ResponseEntity con el usuario actualizado y un mensaje de éxito, o un mensaje de error si no se encuentra.
     */
    @PostMapping("/{userId}/reset-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> resetPassword(@PathVariable Long userId, HttpServletRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("User id " + userId + " not found", HttpStatus.NOT_FOUND, request);
        }
        User user = userOpt.get();
        String defaultPassword = "password123"; // o genera uno seguro dinámico
        user.setPassword(passwordEncoder.encode(defaultPassword));
        userRepository.save(user);
        return new GlobalResponseHandler().handleResponse("Password reset successfully to default: " + defaultPassword, user, HttpStatus.OK, request);
    }

    /**
     * Obtiene un resumen de todos los usuarios.
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param request La petición HTTP.
     * @return ResponseEntity con la lista de resúmenes de usuarios y un mensaje de éxito.
     */
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getUserSummary(HttpServletRequest request) {
        List<User> users = userRepository.findAll();
        List<UserSummary> userSummaries = users.stream()
                .map(user -> new UserSummary(user.getName(), user.getEmail(), user.getEnabled(), user.getRole().getName().toString()))
                .collect(Collectors.toList());

        return new GlobalResponseHandler().handleResponse("Users summary retrieved successfully",
                userSummaries, HttpStatus.OK, request);
    }

    /**
     * Alterna el estado de habilitación de un usuario (enabled/disabled).
     * Requiere que el usuario autenticado tenga el rol 'ADMIN' o 'SUPER_ADMIN'.
     *
     * @param userId  El ID del usuario cuyo estado se va a alternar.
     * @param request La petición HTTP.
     * @return ResponseEntity con el usuario actualizado y un mensaje de éxito, o un mensaje de error si no se encuentra.
     */
    @PatchMapping("/{userId}/toggle-enabled")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> toggleUserEnabled(@PathVariable Long userId, HttpServletRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("User id " + userId + " not found", HttpStatus.NOT_FOUND, request);
        }

        User user = userOpt.get();
        user.setEnabled(!user.getEnabled());
        userRepository.save(user);

        String status = user.getEnabled() ? "enabled" : "disabled";
        return new GlobalResponseHandler().handleResponse("User " + status + " successfully", user, HttpStatus.OK, request);
    }
}
