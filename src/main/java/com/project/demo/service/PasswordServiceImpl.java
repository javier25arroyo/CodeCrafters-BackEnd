package com.project.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    
    private static final String DEFAULT_PASSWORD = "password123";
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    @Override
    public String generateDefaultPassword() {
        return DEFAULT_PASSWORD;
    }
}