package com.project.demo.logic.exceptions;

/**
 * Excepción lanzada cuando un usuario no es encontrado en el sistema.
 */
public class UserNotFoundException extends BaseException {
    private static final String ERROR_CODE = "USER_NOT_FOUND";

    /**
     * Constructor para UserNotFoundException con un mensaje personalizado.
     *
     * @param message   Mensaje de error.
     * @param operation Operación que causó la excepción.
     */
    public UserNotFoundException(String message, String operation) {
        super(message, ERROR_CODE, operation);
    }

    /**
     * Constructor para UserNotFoundException con un ID de usuario.
     *
     * @param userId    El ID del usuario que no fue encontrado.
     * @param operation Operación que causó la excepción.
     */
    public UserNotFoundException(Long userId, String operation) {
        super("User not found with id: " + userId, ERROR_CODE, operation);
    }

    /**
     * Constructor para UserNotFoundException con un correo electrónico de usuario.
     *
     * @param email     El correo electrónico del usuario que no fue encontrado.
     * @param operation Operación que causó la excepción.
     * @param isEmail   Un flag para diferenciar este constructor del constructor con mensaje personalizado.
     */
    public UserNotFoundException(String email, String operation, boolean isEmail) {
        super("User not found with email: " + email, ERROR_CODE, operation);
    }
}