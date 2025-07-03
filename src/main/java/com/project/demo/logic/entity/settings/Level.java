package com.project.demo.logic.entity.settings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.auth.User;
import com.project.demo.logic.entity.history.ActivityLog;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "level")
@Entity
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(name = "points_required")
    private Integer pointsRequired;

    @JsonIgnore
    @OneToMany(mappedBy = "level")
    private List<User> users;


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

    public Integer getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(Integer pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
