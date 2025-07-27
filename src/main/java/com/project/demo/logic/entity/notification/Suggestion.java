package com.project.demo.logic.entity.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.logic.entity.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Representa una sugerencia enviada por un usuario. Esta entidad mapea la tabla 'suggestions' en la
 * base de datos.
 */
@Entity
@Table(name = "suggestions")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(length = 50)
    private String status;

    private LocalDateTime date;

    public Suggestion() {}

    public Suggestion(Integer id, User user, String message, String status, LocalDateTime date) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.status = status;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
