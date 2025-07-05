package com.project.demo.logic.entity.user;

import com.project.demo.logic.entity.auth.User;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;

    private User authUser;

    private long expiresIn;

    
}
