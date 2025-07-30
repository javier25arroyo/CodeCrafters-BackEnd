package com.project.demo.logic.exceptions;

/**
 * Excepción para errores de lógica de negocio.
 * Se utiliza para encapsular errores que ocurren durante la ejecución de la lógica de negocio.
 */
public class BusinessException extends BaseException {
    private static final String ERROR_CODE = "BUSINESS_ERROR";

    /**
     * Constructor para BusinessException.
     *
     * @param message   Mensaje de error.
     * @param operation Operación que causó la excepción.
     */
    public BusinessException(String message, String operation) {
        super(message, ERROR_CODE, operation);
    }

    /**
     * Constructor para BusinessException con una causa.
     *
     * @param message   Mensaje de error.
     * @param operation Operación que causó la excepción.
     * @param cause     Causa de la excepción.
     */
    public BusinessException(String message, String operation, Throwable cause) {
        super(message, ERROR_CODE, operation, cause);
    }

    /**
     * Excepción para rol no encontrado.
     */
    public static class RoleNotFoundException extends BusinessException {
        /**
         * Constructor para RoleNotFoundException.
         *
         * @param roleName  Nombre del rol no encontrado.
         * @param operation Operación que causó la excepción.
         */
        public RoleNotFoundException(String roleName, String operation) {
            super("Role not found: " + roleName, operation);
        }
    }

    /**
     * Excepción para errores en el servicio de correo electrónico.
     */
    public static class EmailServiceException extends BusinessException {
        /**
         * Constructor para EmailServiceException.
         *
         * @param operation Operación que causó la excepción.
         * @param cause     Causa de la excepción.
         */
        public EmailServiceException(String operation, Throwable cause) {
            super("Failed to send email", operation, cause);
        }
    }

    /**
     * Excepción para usuario ya existente.
     */
    public static class UserAlreadyExistsException extends BusinessException {
        /**
         * Constructor para UserAlreadyExistsException.
         *
         * @param email     Email del usuario que ya existe.
         * @param operation Operación que causó la excepción.
         */
        public UserAlreadyExistsException(String email, String operation) {
            super("User already exists with email: " + email, operation);
        }
    }

    /**
     * Excepción para permisos insuficientes.
     */
    public static class InsufficientPermissionsException extends BusinessException {
        /**
         * Constructor para InsufficientPermissionsException.
         *
         * @param operation Operación que causó la excepción.
         */
        public InsufficientPermissionsException(String operation) {
            super("Insufficient permissions for this operation", operation);
        }
    }
}
