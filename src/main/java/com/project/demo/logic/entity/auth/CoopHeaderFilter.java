package com.project.demo.logic.entity.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para agregar las cabeceras COOP y COEP a las respuestas HTTP.
 * Esto es necesario para permitir los pop-ups de autenticación de Google.
 */
@Component
public class CoopHeaderFilter extends OncePerRequestFilter {

    /**
     * Constructor por defecto.
     */
    public CoopHeaderFilter() {
    }

    /**
     * Agrega las cabeceras COOP y COEP a la respuesta HTTP.
     *
     * @param req     La solicitud HTTP.
     * @param res     La respuesta HTTP.
     * @param chain   La cadena de filtros.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String requestURI = req.getRequestURI();

        // Se configura COOP para permitir popups de Google OAuth
        res.setHeader("Cross-Origin-Opener-Policy", "same-origin-allow-popups");
        res.setHeader("Cross-Origin-Embedder-Policy", "unsafe-none");

        chain.doFilter(req, res);
    }
}