package com.project.demo.logic.entity.auth;

/**
 * Representa una solicitud de token de Google.
 * Utilizado para encapsular el token de ID de Google enviado desde el frontend.
 */
public class GoogleTokenRequest {
    private String idToken;

    /**
     * Constructor por defecto.
     */
    public GoogleTokenRequest() {}

    /**
     * Constructor para crear una instancia de GoogleTokenRequest con un token de ID.
     *
     * @param idToken El token de ID de Google.
     */
    public GoogleTokenRequest(String idToken) {
        this.idToken = idToken;
    }

    /**
     * Obtiene el token de ID de Google.
     *
     * @return El token de ID de Google.
     */
    public String getIdToken() {
        return idToken;
    }

    /**
     * Establece el token de ID de Google.
     *
     * @param idToken El nuevo token de ID de Google.
     */
    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}