package com.project.demo.logic.entity.user;

/**
 * Representa un resumen de la información de un usuario.
 * Utilizado para mostrar datos básicos del usuario sin exponer información sensible.
 */
public class UserSummary {
    private String name;
    private String email;
    private boolean enabled;
    private String role;

    /**
     * Constructor por defecto.
     */
    public UserSummary() {
    }

    /**
     * Constructor para crear una instancia de UserSummary con todos los parámetros.
     *
     * @param name    Nombre del usuario.
     * @param email   Correo electrónico del usuario.
     * @param enabled Estado de habilitación del usuario.
     * @param role    Rol del usuario.
     */
    public UserSummary(String name, String email, boolean enabled, String role) {
        this.name = name;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param name El nuevo nombre del usuario.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El nuevo correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Indica si el usuario está habilitado.
     *
     * @return True si el usuario está habilitado, false en caso contrario.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Establece si el usuario está habilitado.
     *
     * @param enabled El nuevo estado de habilitación del usuario.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public String getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param role El nuevo rol del usuario.
     */
    public void setRole(String role) {
        this.role = role;
    }
}