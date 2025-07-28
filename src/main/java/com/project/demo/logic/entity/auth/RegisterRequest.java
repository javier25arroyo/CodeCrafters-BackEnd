
package com.project.demo.logic.entity.auth;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private boolean isCaregiver;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isCaregiver() { return isCaregiver; }
    public void setCaregiver(boolean isCaregiver) { this.isCaregiver = isCaregiver; }
}
