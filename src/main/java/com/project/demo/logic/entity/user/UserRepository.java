package com.project.demo.logic.entity.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio para la entidad {@link User}. Proporciona métodos para realizar operaciones CRUD
 * sobre los usuarios y consultas personalizadas.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Busca usuarios cuyo nombre contenga un carácter o secuencia de caracteres específica
     * (ignorando mayúsculas/minúsculas).
     *
     * @param character El carácter o secuencia de caracteres a buscar en el nombre del usuario.
     * @return Una lista de usuarios que coinciden con el criterio de búsqueda.
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE %?1%")
    List<User> findUsersWithCharacterInName(String character);

    /**
     * Busca un usuario por su nombre exacto.
     *
     * @param name El nombre del usuario a buscar.
     * @return Un {@link Optional} que contiene el usuario si se encuentra, o vacío si no.
     */
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findByName(String name);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un {@link Optional} que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findByEmail(String email);

    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String email, Pageable pageable);
}
