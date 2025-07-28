package com.project.demo.logic.exceptions;

import com.project.demo.service.LoggingService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y procesa excepciones lanzadas por los controladores y servicios,
 * devolviendo respuestas estandarizadas de tipo {@link ProblemDetail}.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @Autowired
    private LoggingService loggingService;

    /**
     * Constructor por defecto.
     */
    public GlobalExceptionHandler() {
    }
    /**
     * Maneja excepciones personalizadas de la aplicación.
     *
     * @param exception La excepción base que ha sido lanzada.
     * @param request La petición HTTP.
     * @return Un {@link ProblemDetail} que describe el error.
     */
    @ExceptionHandler(BaseException.class)
    public ProblemDetail handleBaseException(BaseException exception, HttpServletRequest request) {
        loggingService.logError(exception, "GlobalExceptionHandler", "handleBaseException");
        
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(getStatusCodeForException(exception)), 
                exception.getMessage());
        
        errorDetail.setProperty("errorCode", exception.getErrorCode());
        errorDetail.setProperty("operation", exception.getOperation());
        errorDetail.setProperty("timestamp", exception.getTimestamp().toString());
        errorDetail.setProperty("path", request.getRequestURI());
        
        return errorDetail;
    }
    
    /**
     * Maneja excepciones relacionadas con la seguridad y otras excepciones generales.
     * Convierte las excepciones en un formato de {@link ProblemDetail} con detalles específicos.
     *
     * @param exception La excepción que ha sido lanzada.
     * @param request La petición HTTP.
     * @return Un {@link ProblemDetail} que describe el error.
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception, HttpServletRequest request) {
        ProblemDetail errorDetail = null;

        // Log the error with detailed context
        loggingService.logError(exception, "GlobalExceptionHandler", "handleSecurityException", "Security/System Exception");

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

            return errorDetail;
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }

        if (exception instanceof UserNotFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
            errorDetail.setProperty("description", "The requested user was not found");
        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error.");
        }
        
        // Add common properties to all error responses
        errorDetail.setProperty("path", request.getRequestURI());
        errorDetail.setProperty("timestamp", java.time.LocalDateTime.now().toString());

        return errorDetail;
    }
    
    /**
     * Determina el código de estado HTTP apropiado para una excepción personalizada.
     *
     * @param exception La excepción personalizada.
     * @return El código de estado HTTP.
     */
    private int getStatusCodeForException(BaseException exception) {
        if (exception instanceof UserNotFoundException) {
            return 404;
        } else if (exception instanceof ValidationException) {
            return 400;
        } else if (exception instanceof AuthenticationException) {
            return 401;
        } else if (exception instanceof BusinessException.InsufficientPermissionsException) {
            return 403;
        } else if (exception instanceof BusinessException) {
            return 422; // Unprocessable Entity
        }
        return 500;
    }
}
