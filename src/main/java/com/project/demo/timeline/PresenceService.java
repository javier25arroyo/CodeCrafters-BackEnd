package com.project.demo.timeline;

import com.project.demo.timeline.model.WsEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PresenceService {

    private final GameService game;
    private final SimpMessagingTemplate template;

    private final Map<String, String[]> sessions = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Set<String>>> roomPresence = new ConcurrentHashMap<>();

    public PresenceService(GameService game, SimpMessagingTemplate template) {
        this.game = game;
        this.template = template;
    }

    @EventListener
    public void onSubscribe(SessionSubscribeEvent ev) {
        var acc = StompHeaderAccessor.wrap(ev.getMessage());
        var dest = acc.getDestination();
        var user = acc.getUser();
        var sid  = acc.getSessionId();
        if (dest == null || user == null || sid == null) return;
        if (!dest.startsWith("/topic/room.")) return;

        String roomId = dest.substring("/topic/room.".length());
        String userId = user.getName();

        sessions.put(sid, new String[]{roomId, userId});
        roomPresence
                .computeIfAbsent(roomId, r -> new ConcurrentHashMap<>())
                .computeIfAbsent(userId, u -> ConcurrentHashMap.newKeySet())
                .add(sid);

        var r = game.get(roomId);
        if (r == null) return;

        if (r.paused) {
            var users = roomPresence.getOrDefault(roomId, Map.of());
            boolean allOnline = r.players.keySet().stream()
                    .allMatch(uid -> users.getOrDefault(uid, Set.of()).size() > 0);
            if (allOnline) {
                r.paused = false;
                long now = System.currentTimeMillis();
                r.turnDeadlineEpochMs = now + Math.max(0, r.turnDurationMs);
                r.gameDeadlineEpochMs = now + Math.max(0, r.gameDurationMs);
                template.convertAndSend("/topic/room." + roomId,
                        new WsEvent("RESUMED", game.snapshot(r)));
            }
        }
    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent ev) {
        String sid = ev.getSessionId();
        if (sid == null) return;

        var info = sessions.remove(sid);
        if (info == null) return;
        String roomId = info[0], userId = info[1];

        var users = roomPresence.get(roomId);
        if (users != null) {
            var set = users.get(userId);
            if (set != null) {
                set.remove(sid);
                if (set.isEmpty()) users.remove(userId);
            }
        }

        var r = game.get(roomId);
        if (r == null) return;

        boolean someoneOffline = r.players.keySet().stream()
                .anyMatch(uid -> users == null || users.getOrDefault(uid, Set.of()).isEmpty());

        if (someoneOffline) {
            r.paused = true;
            long now = System.currentTimeMillis();
            long turnLeft = r.turnDeadlineEpochMs > 0 ? Math.max(0, r.turnDeadlineEpochMs - now) : r.turnDurationMs;
            long gameLeft = r.gameDeadlineEpochMs > 0 ? Math.max(0, r.gameDeadlineEpochMs - now) : r.gameDurationMs;

            r.turnDurationMs = turnLeft;
            r.gameDurationMs = gameLeft;
            r.turnDeadlineEpochMs = 0;
            r.gameDeadlineEpochMs = 0;

            template.convertAndSend("/topic/room." + roomId,
                    new WsEvent("PAUSED", game.snapshot(r)));
        }
    }
}
