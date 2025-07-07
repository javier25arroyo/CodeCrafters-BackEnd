package com.project.demo.logic.entity.settings;

import jakarta.persistence.*;

@Entity
@Table(name = "levels")
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int points_required;

    public Level() {
    }

    public Level(Long id, String name, int points_required) {
        this.id = id;
        this.name = name;
        this.points_required = points_required;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints_required() {
        return points_required;
    }

    public void setPoints_required(int points_required) {
        this.points_required = points_required;
    }
}