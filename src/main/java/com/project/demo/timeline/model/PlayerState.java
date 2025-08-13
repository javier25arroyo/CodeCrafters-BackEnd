package com.project.demo.timeline.model;

import java.util.ArrayList; import java.util.List;
public class PlayerState {
    public final String userId; public final String name; public final List<Card> hand = new ArrayList<>();
    public PlayerState(String userId, String name){ this.userId=userId; this.name=name; }
}
