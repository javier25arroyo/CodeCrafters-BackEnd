package com.project.demo.timeline;

import com.project.demo.timeline.model.WsEvent;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/timeline")
public class TimelineController {

    private final GameService game;
    private final SimpMessagingTemplate template;

    public TimelineController(GameService game, SimpMessagingTemplate template){
        this.game = game;
        this.template = template;
    }

    @PostMapping("/rooms")
    public Map<String, String> createRoom() {
        return Map.of("roomId", game.createRoom().id);
    }

    @PostMapping("/rooms/{roomId}/join")
    public Map<String, Boolean> join(@PathVariable String roomId, Principal p){
        var state = game.join(roomId, p);
        template.convertAndSend("/topic/room." + roomId, new WsEvent("JOINED", state));
        return Map.of("ok", true);
    }

    @MessageMapping("/room/{roomId}/action")
    public void onAction(@DestinationVariable String roomId,
                         @Payload Map<String, Object> a,
                         Principal p) {
        String type = String.valueOf(a.get("type"));
        Map<String, Object> payload;

        switch (type) {
            case "START"   -> payload = game.start(roomId, p);
            case "PLAY_CARD" -> payload = game.play(
                    roomId, p,
                    (String) a.get("cardId"),
                    ((Number) a.getOrDefault("position", 0)).intValue()
            );
            case "JOIN"    -> payload = game.join(roomId, p);
            case "TIMEOUT" -> payload = game.timeout(roomId);
            default        -> { return; }
        }

        WsEvent event = "PLAY_CARD".equals(type)
                ? new WsEvent("PLAY_RESULT", payload)
                : new WsEvent("START".equals(type) ? "STARTED" : "STATE", payload);

        template.convertAndSend("/topic/room." + roomId, event);
    }

    @GetMapping("/rooms/{roomId}/state")
    public Map<String, Object> getState(@PathVariable String roomId) {
        return game.snapshot(game.get(roomId));
    }
}
