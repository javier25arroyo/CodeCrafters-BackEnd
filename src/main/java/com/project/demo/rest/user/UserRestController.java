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
        user.setEnabled(true);
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
    @PreAuthorize("isAuthenticated()")
    public User authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

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
     * Obtiene una lista paginada de usuarios en formato resumido, con opción de búsqueda por nombre o email.
     * Solo accesible para roles ADMIN y SUPER_ADMIN.
     *
     * @param page    Número de la página a recuperar (por defecto 1).
     * @param size    Tamaño de la página (por defecto 10).
     * @param search  Término de búsqueda opcional por nombre o correo.
     * @param request Objeto HTTP usado para construir metadata de la respuesta.
     * @return Lista paginada de {@link UserSummary} y metadata asociada.
     */
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getUserSummaryList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> userPage;

        if (search != null && !search.trim().isEmpty()) {
            userPage = userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        List<UserSummary> summaries = userPage.getContent().stream()
                .map(user -> new UserSummary(
                        user.getId().intValue(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole() != null ? user.getRole().getName().name() : "N/A",
                        user.isEnabled()
                ))
                .toList();

        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        meta.setTotalPages(userPage.getTotalPages());
        meta.setTotalElements(userPage.getTotalElements());
        meta.setPageNumber(userPage.getNumber() + 1);
        meta.setPageSize(userPage.getSize());

        return new GlobalResponseHandler().handleResponse(
                "User summaries retrieved successfully",
                summaries,
                HttpStatus.OK,
                meta
        );
    }
    @PatchMapping("/{userId}/enable")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> enableUser(@PathVariable Long userId, HttpServletRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("User not found", HttpStatus.NOT_FOUND, request);
        }
        User user = userOpt.get();
        user.setEnabled(true);
        userRepository.save(user);
        return new GlobalResponseHandler().handleResponse("User enabled", user, HttpStatus.OK, request);
    }

    @PatchMapping("/{userId}/disable")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> disableUser(@PathVariable Long userId, HttpServletRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("User not found", HttpStatus.NOT_FOUND, request);
        }
        User user = userOpt.get();
        user.setEnabled(false);
        userRepository.save(user);
        return new GlobalResponseHandler().handleResponse("User disabled", user, HttpStatus.OK, request);
}



}