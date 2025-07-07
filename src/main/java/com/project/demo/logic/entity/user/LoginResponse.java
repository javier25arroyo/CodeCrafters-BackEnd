package com.project.demo.logic.entity.user;

public class LoginResponse {
    private String token;
    private User authUser;
    private long expiresIn;

    public LoginResponse() {
    }

    public LoginResponse(String token, User authUser, long expiresIn) {
        this.token = token;
        this.authUser = authUser;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
