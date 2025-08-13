package com.project.demo.timeline.model;

import java.util.*;

public class RoomState {
    public final String id;

    public final Map<String, PlayerState> players = new LinkedHashMap<>();
    public final List<String> turnOrder = new ArrayList<>();
    public int currentTurnIdx = 0;

    public String phase = "LOBBY";
    public String hostId;

    public final List<Card> timeline = new ArrayList<>();
    public final Deque<Card> deck = new ArrayDeque<>();
    public final List<Card> discard = new ArrayList<>();

    public boolean paused = false;

    public long turnDurationMs = 60_000;
    public long gameDurationMs = 20 * 60_000;

    public long turnDeadlineEpochMs = 0L;
    public long gameDeadlineEpochMs = 0L;

    public String winnerId;

    public RoomState(String id){ this.id = id; }

    public String currentPlayerId(){
        return turnOrder.isEmpty() ? null : turnOrder.get(currentTurnIdx);
    }
}
