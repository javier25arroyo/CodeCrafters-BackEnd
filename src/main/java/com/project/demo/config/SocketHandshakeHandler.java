package com.project.demo.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class SocketHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest req, WebSocketHandler wsHandler, Map<String, Object> attrs) {
        Object u = attrs.get("principal");
        if (u instanceof Principal p) return p;
        if (u instanceof String s) return () -> s;
        return null;
    }
}
