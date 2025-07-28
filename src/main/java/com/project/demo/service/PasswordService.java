package com.project.demo.service;

public interface PasswordService {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
    String generateDefaultPassword();
}