package com.project.demo.logic.entity.user;

/**
 * Representa un resumen de la información básica de un usuario.
 * Se utiliza para mostrar listas de usuarios en la interfaz de administración sin exponer información sensible.
 */
public class UserSummary {

    /** Identificador único del usuario. */
    private Integer id;

    /** Nombre del usuario. */
    private String name;

    /** Correo electrónico del usuario. */
    private String email;

    /** Rol del usuario (ejemplo: ADMIN, USER, etc). */
    private String role;

    /** Indica si el usuario está habilitado. */
    private boolean enabled;

    /**
     * Constructor para crear un resumen de usuario.
     *
     * @param id ID del usuario
     * @param name Nombre del usuario
     * @param email Correo electrónico del usuario
     * @param role Rol asignado
     * @param enabled Estado de habilitación
     */
    public UserSummary(Integer id, String name, String email, String role, boolean enabled) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }

    // Getters y setters documentados

    /** @return ID del usuario */
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    /** @return Nombre del usuario */
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    /** @return Correo electrónico del usuario */
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    /** @return Rol del usuario */
    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    /** @return Estado de habilitación del usuario */
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
