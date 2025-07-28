package com.project.demo.service;

/**
 * Interfaz para el servicio de gestión de contraseñas.
 * Define las operaciones para codificar, verificar y generar contraseñas.
 */
public interface PasswordService {
    /**
     * Codifica una contraseña en texto plano.
     *
     * @param password La contraseña en texto plano a codificar.
     * @return La contraseña codificada.
     */
    String encode(String password);

    /**
     * Verifica si una contraseña en texto plano coincide con una contraseña codificada.
     *
     * @param rawPassword     La contraseña en texto plano.
     * @param encodedPassword La contraseña codificada.
     * @return True si las contraseñas coinciden, false en caso contrario.
     */
    boolean matches(String rawPassword, String encodedPassword);

    /**
     * Genera una contraseña por defecto.
     *
     * @return La contraseña por defecto generada.
     */
    String generateDefaultPassword();
}