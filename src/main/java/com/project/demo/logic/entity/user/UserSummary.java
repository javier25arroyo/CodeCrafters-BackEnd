package com.project.demo.logic.entity.user;

public class UserSummary {
    private String name;
    private String email;
    private boolean enabled;
    private String role;

    public UserSummary(String name, String email, boolean enabled, String role) {
        this.name = name;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
