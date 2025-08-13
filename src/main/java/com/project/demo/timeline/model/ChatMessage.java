package com.project.demo.timeline.model;

public record ChatMessage(String userId, String text, long ts, String emoji) {}
