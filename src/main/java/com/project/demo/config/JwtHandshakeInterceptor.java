package com.project.demo.config;

import com.project.demo.logic.entity.auth.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.*;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import java.security.Principal;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtService jwtService;
    public JwtHandshakeInterceptor(JwtService jwtService){ this.jwtService = jwtService; }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest servlet) {
            HttpServletRequest http = servlet.getServletRequest();
            String token = http.getParameter("token");
            if (token == null || token.isBlank()) return false;
            try {
                String username = jwtService.extractUsername(token);
                attributes.put("principal", (Principal) () -> username);
                return true;
            } catch (Exception e) { return false; }
        }
        return false;
    }

    @Override public void afterHandshake(ServerHttpRequest r, ServerHttpResponse s, WebSocketHandler w, Exception e) {}
}
