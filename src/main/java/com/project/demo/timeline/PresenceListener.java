package com.project.demo.timeline;

import com.project.demo.timeline.model.WsEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PresenceListener {
    private final GameService game;
    private final SimpMessagingTemplate template;
    private final Map<String, String[]> sessions = new ConcurrentHashMap<>();

    public PresenceListener(GameService game, SimpMessagingTemplate template) {
        this.game = game; this.template = template;
    }

    @EventListener
    public void onConnect(SessionConnectEvent e){}

    @EventListener
    public void onDisconnect(SessionDisconnectEvent e){
        var id = e.getSessionId();
        var data = sessions.remove(id);
        if (data == null) return;
        String roomId = data[0], userId = data[1];
        var r = game.get(roomId);
        if (r == null) return;
        r.paused = true;
        long now = System.currentTimeMillis();
        r.turnDurationMs = Math.max(0, r.turnDeadlineEpochMs - now);
        r.gameDurationMs = Math.max(0, r.gameDeadlineEpochMs - now);
        r.turnDeadlineEpochMs = 0;
        r.gameDeadlineEpochMs = 0;
        template.convertAndSend("/topic/room."+roomId, new WsEvent("PAUSED", game.snapshot(r)));
    }

    public void registerSession(String sessionId, String roomId, Principal p){
        sessions.put(sessionId, new String[]{ roomId, p.getName() });
        var r = game.get(roomId);
        if (r != null && r.paused) {
            r.paused = false;
            long now = System.currentTimeMillis();
            r.turnDeadlineEpochMs = now + r.turnDurationMs;
            r.gameDeadlineEpochMs = now + r.gameDurationMs;
            template.convertAndSend("/topic/room."+roomId, new WsEvent("RESUMED", game.snapshot(r)));
        }
    }
}
