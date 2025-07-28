package com.project.demo.logic.exceptions;

import java.util.List;

/**
 * Excepción para errores de validación de datos.
 * Se utiliza para encapsular errores relacionados con la validación de entradas de usuario.
 */
public class ValidationException extends BaseException {
    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private final List<String> validationErrors;

    /**
     * Constructor para ValidationException con un solo mensaje de error.
     *
     * @param message   Mensaje de error.
     * @param operation Operación que causó la excepción.
     */
    public ValidationException(String message, String operation) {
        super(message, ERROR_CODE, operation);
        this.validationErrors = List.of(message);
    }

    /**
     * Constructor para ValidationException con una lista de mensajes de error.
     *
     * @param validationErrors Lista de mensajes de error de validación.
     * @param operation        Operación que causó la excepción.
     */
    public ValidationException(List<String> validationErrors, String operation) {
        super("Validation failed: " + String.join(", ", validationErrors), ERROR_CODE, operation);
        this.validationErrors = validationErrors;
    }

    /**
     * Obtiene la lista de errores de validación.
     *
     * @return La lista de errores de validación.
     */
    public List<String> getValidationErrors() {
        return validationErrors;
    }

    /**
     * Excepción para formato de correo electrónico inválido.
     */
    public static class InvalidEmailException extends ValidationException {
        /**
         * Constructor para InvalidEmailException.
         *
         * @param email     El correo electrónico con formato inválido.
         * @param operation Operación que causó la excepción.
         */
        public InvalidEmailException(String email, String operation) {
            super("Invalid email format: " + email, operation);
        }
    }

    /**
     * Excepción para contraseña inválida.
     */
    public static class InvalidPasswordException extends ValidationException {
        /**
         * Constructor para InvalidPasswordException.
         *
         * @param reason    La razón por la cual la contraseña es inválida.
         * @param operation Operación que causó la excepción.
         */
        public InvalidPasswordException(String reason, String operation) {
            super("Invalid password: " + reason, operation);
        }
    }

    /**
     * Excepción para campo requerido faltante.
     */
    public static class RequiredFieldException extends ValidationException {
        /**
         * Constructor para RequiredFieldException.
         *
         * @param fieldName El nombre del campo requerido que falta.
         * @param operation Operación que causó la excepción.
         */
        public RequiredFieldException(String fieldName, String operation) {
            super("Required field is missing: " + fieldName, operation);
        }
    }
}