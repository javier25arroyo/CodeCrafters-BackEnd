package com.project.demo.logic.entity.user;

/**
 * Representa la respuesta devuelta al usuario después de un inicio de sesión exitoso. Contiene el
 * token de autenticación, la información del usuario autenticado y el tiempo de expiración del
 * token.
 */
public class LoginResponse {
    private String token;
    private User authUser;
    private long expiresIn;

    /** Constructor por defecto. */
    public LoginResponse() {}

    /**
     * Constructor para crear una instancia de LoginResponse con todos los detalles.
     *
     * @param token El token JWT generado para la sesión.
     * @param authUser El objeto {@link User} del usuario autenticado.
     * @param expiresIn El tiempo en milisegundos hasta que el token expire.
     */
    public LoginResponse(String token, User authUser, long expiresIn) {
        this.token = token;
        this.authUser = authUser;
        this.expiresIn = expiresIn;
    }

    /**
     * Obtiene el token JWT.
     *
     * @return El token JWT.
     */
    public String getToken() {
        return token;
    }

    /**
     * Establece el token JWT.
     *
     * @param token El token JWT a establecer.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Obtiene el usuario autenticado.
     *
     * @return El objeto {@link User} del usuario autenticado.
     */
    public User getAuthUser() {
        return authUser;
    }

    /**
     * Establece el usuario autenticado.
     *
     * @param authUser El objeto {@link User} del usuario autenticado a establecer.
     */
    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }

    /**
     * Obtiene el tiempo de expiración del token en milisegundos.
     *
     * @return El tiempo de expiración.
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Establece el tiempo de expiración del token en milisegundos.
     *
     * @param expiresIn El tiempo de expiración a establecer.
     */
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
