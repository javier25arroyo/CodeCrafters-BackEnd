package com.project.demo.logic.entity.http;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de respuestas HTTP para la aplicación.
 * Intercepta las respuestas de los controladores para estandarizar el formato de salida,
 * incluyendo metadatos como el método de la petición y la URL.
 */
@RestControllerAdvice
public class GlobalResponseHandler {
    /**
     * Constructor por defecto.
     */
    public GlobalResponseHandler() {
    }
    /**
     * Maneja y estandariza las respuestas HTTP con un cuerpo de respuesta.
     *
     * @param message El mensaje descriptivo de la respuesta.
     * @param body    El cuerpo de la respuesta, que puede ser cualquier tipo de dato o una instancia de {@link HttpResponse}.
     * @param status  El estado HTTP de la respuesta.
     * @param request La petición HTTP original.
     * @param <T>     El tipo del cuerpo de la respuesta.
     * @return Una {@link ResponseEntity} estandarizada.
     */
    @ResponseBody
    public <T> ResponseEntity<?> handleResponse(String message, T body, HttpStatus status, HttpServletRequest request) {
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        if (body instanceof HttpResponse) {
            HttpResponse<?> response = (HttpResponse<?>) body;
            response.setMeta(meta);
            return new ResponseEntity<>(response, status);
        }
        HttpResponse<T> response = new HttpResponse<>(message, body, meta);
        return  new ResponseEntity<>(response, status);
    }

    /**
     * Maneja y estandariza las respuestas HTTP sin un cuerpo de respuesta explícito.
     *
     * @param message El mensaje descriptivo de la respuesta.
     * @param status  El estado HTTP de la respuesta.
     * @param request La petición HTTP original.
     * @param <T>     El tipo genérico (no utilizado en este caso, pero mantiene la consistencia de la firma).
     * @return Una {@link ResponseEntity} estandarizada.
     */
    @ResponseBody
    public <T> ResponseEntity<?> handleResponse(String message, HttpStatus status, HttpServletRequest request) {
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        HttpResponse<?> response = new HttpResponse<>(message, meta);
        return  new ResponseEntity<>(response, status);
    }

    /**
     * Maneja y estandariza las respuestas HTTP con un cuerpo de respuesta y metadatos ya definidos.
     *
     * @param message El mensaje descriptivo de la respuesta.
     * @param body    El cuerpo de la respuesta, que puede ser cualquier tipo de dato o una instancia de {@link HttpResponse}.
     * @param status  El estado HTTP de la respuesta.
     * @param meta    Los metadatos de la respuesta.
     * @param <T>     El tipo del cuerpo de la respuesta.
     * @return Una {@link ResponseEntity} estandarizada.
     */
    @ResponseBody
    public <T> ResponseEntity<?> handleResponse(String message, T body, HttpStatus status, Meta meta) {
        if (body instanceof HttpResponse) {
            HttpResponse<?> response = (HttpResponse<?>) body;
            response.setMeta(meta);
            return new ResponseEntity<>(response, status);
        }
        HttpResponse<T> response = new HttpResponse<>(message, body, meta);
        return  new ResponseEntity<>(response, status);
    }
}

