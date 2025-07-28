package com.project.demo.logic.exceptions;

/**
 * Excepción base para errores de autenticación.
 * Se utiliza para encapsular errores relacionados con la autenticación de usuarios.
 */
public class AuthenticationException extends BaseException {
    private static final String ERROR_CODE = "AUTH_ERROR";

    /**
     * Constructor para AuthenticationException.
     *
     * @param message   Mensaje de error.
     * @param operation Operación que causó la excepción.
     */
    public AuthenticationException(String message, String operation) {
        super(message, ERROR_CODE, operation);
    }

    /**
     * Constructor para AuthenticationException con una causa.
     *
     * @param message   Mensaje de error.
     * @param operation Operación que causó la excepción.
     * @param cause     Causa de la excepción.
     */
    public AuthenticationException(String message, String operation, Throwable cause) {
        super(message, ERROR_CODE, operation, cause);
    }

    /**
     * Excepción para credenciales inválidas.
     */
    public static class InvalidCredentialsException extends AuthenticationException {
        /**
         * Constructor para InvalidCredentialsException.
         *
         * @param operation Operación que causó la excepción.
         */
        public InvalidCredentialsException(String operation) {
            super("Invalid username or password", operation);
        }
    }

    /**
     * Excepción para token expirado.
     */
    public static class TokenExpiredException extends AuthenticationException {
        /**
         * Constructor para TokenExpiredException.
         *
         * @param operation Operación que causó la excepción.
         */
        public TokenExpiredException(String operation) {
            super("Authentication token has expired", operation);
        }
    }

    /**
     * Excepción para token inválido.
     */
    public static class InvalidTokenException extends AuthenticationException {
        /**
         * Constructor para InvalidTokenException.
         *
         * @param operation Operación que causó la excepción.
         */
        public InvalidTokenException(String operation) {
            super("Invalid authentication token", operation);
        }
    }
}
