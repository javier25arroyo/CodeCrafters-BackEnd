package com.project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de gestión de contraseñas.
 * Utiliza Spring Security para codificar y verificar contraseñas.
 */
@Service
public class PasswordServiceImpl implements PasswordService {

    private static final String DEFAULT_PASSWORD = "password123";

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor por defecto.
     */
    public PasswordServiceImpl() {
    }

    /**
     * Codifica una contraseña en texto plano.
     *
     * @param password La contraseña en texto plano a codificar.
     * @return La contraseña codificada.
     */
    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Verifica si una contraseña en texto plano coincide con una contraseña codificada.
     *
     * @param rawPassword     La contraseña en texto plano.
     * @param encodedPassword La contraseña codificada.
     * @return True si las contraseñas coinciden, false en caso contrario.
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Genera una contraseña por defecto.
     *
     * @return La contraseña por defecto generada.
     */
    @Override
    public String generateDefaultPassword() {
        return DEFAULT_PASSWORD;
    }
}