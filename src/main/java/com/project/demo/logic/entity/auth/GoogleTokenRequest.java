package com.project.demo.logic.entity.auth;

public class GoogleTokenRequest {
    private String idToken;

    public GoogleTokenRequest() {}

    public GoogleTokenRequest(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
