package com.project.demo.logic.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;

    @JsonProperty("isCaregiver")
    private boolean caregiver;

    private String phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isCaregiver() { return caregiver; }
    public void setCaregiver(boolean caregiver) { this.caregiver = caregiver; }
}
