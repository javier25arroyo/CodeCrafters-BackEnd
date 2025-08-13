package com.project.demo.config;

import com.project.demo.logic.entity.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired private JwtService jwtService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .addInterceptors(new JwtHandshakeInterceptor(jwtService))
                .setHandshakeHandler(new SocketHandshakeHandler())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration reg) {
        reg.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                var headers = StompHeaderAccessor.wrap(message);
                if (StompCommand.SUBSCRIBE.equals(headers.getCommand())) {
                    var dest = headers.getDestination();
                    if (dest != null && dest.startsWith("/topic/room.")) {
                        String roomId = dest.substring("/topic/room.".length());
                        headers.getSessionAttributes().put("roomId", roomId);
                    }
                }
                return message;
            }
        });
    }
}
