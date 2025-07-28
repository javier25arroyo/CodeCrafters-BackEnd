package com.project.demo.rest.user;

import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.http.Meta;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserSummary;
import com.project.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserService userService;

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
        Page<User> usersPage = userService.getAllUsers(pageable);
        
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        meta.setTotalPages(usersPage.getTotalPages());
        meta.setTotalElements(usersPage.getTotalElements());
        meta.setPageNumber(usersPage.getNumber() + 1);
        meta.setPageSize(usersPage.getSize());

        return new GlobalResponseHandler().handleResponse("Users retrieved successfully",
                usersPage.getContent(), HttpStatus.OK, meta);
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
        User createdUser = userService.createUser(user);
        return new GlobalResponseHandler().handleResponse("User updated successfully",
                createdUser, HttpStatus.OK, request);
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
        Optional<User> updatedUser = userService.updateUser(userId, user);
        
        if (updatedUser.isPresent()) {
            return new GlobalResponseHandler().handleResponse("User updated successfully",
                    updatedUser.get(), HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("User id " + userId + " not found",
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
        Optional<User> foundUser = userService.findUserById(userId);
        if(foundUser.isPresent()) {
            userService.deleteUser(userId);
            return new GlobalResponseHandler().handleResponse("User deleted successfully",
                    foundUser.get(), HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("Order id " + userId + " not found",
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
        return userService.getCurrentUser();
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
        Optional<User> userOpt = userService.findUserById(userId);
        if (userOpt.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("User id " + userId + " not found", HttpStatus.NOT_FOUND, request);
        }
        
        Optional<User> updatedUser = userService.resetPasswordAndGetUser(userId);
        String defaultPassword = "password123";
        return new GlobalResponseHandler().handleResponse("Password reset successfully to default: " + defaultPassword, updatedUser.get(), HttpStatus.OK, request);
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
        List<UserSummary> userSummaries = userService.getUserSummary();
        
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
        Optional<User> updatedUser = userService.toggleUserEnabled(userId);
        
        if (updatedUser.isPresent()) {
            User user = updatedUser.get();
            String status = user.getEnabled() ? "enabled" : "disabled";
            return new GlobalResponseHandler().handleResponse("User " + status + " successfully", 
                    user, HttpStatus.OK, request);
        } else {
            return new GlobalResponseHandler().handleResponse("User id " + userId + " not found", 
                    HttpStatus.NOT_FOUND, request);
        }
    }
}
