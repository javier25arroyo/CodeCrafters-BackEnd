package com.project.demo.logic.entity.auth;

/**
 * Clase que representa una solicitud de restablecimiento de contraseña.
 * Contiene el correo electrónico del usuario que solicita el restablecimiento.
 */
public class PasswordResetRequest {


    private String email;

    /**
     * Constructor por defecto.
     */
    public PasswordResetRequest() {
    }

    /**
     * Constructor para crear una instancia de PasswordResetRequest.
     *
     * @param email El correo electrónico del usuario que solicita el restablecimiento.
     */
    public PasswordResetRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
