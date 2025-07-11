package com.project.demo.logic.entity.rol;

import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad {@link Role}.
 * Proporciona métodos para realizar operaciones CRUD sobre los roles y buscar roles por su nombre.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    /**
     * Busca un rol por su nombre.
     *
     * @param name El nombre del rol, de tipo {@link RoleEnum}.
     * @return Un {@link Optional} que contiene el rol si se encuentra, o vacío si no.
     */
    Optional<Role> findByName(RoleEnum name);
}
