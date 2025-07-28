package com.project.demo.service;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.entity.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordService.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long userId, User user) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setEmail(user.getEmail());
                    
                    if (user.getRole() != null) {
                        existingUser.setRole(user.getRole());
                    }
                    
                    if (user.getPassword() != null && !user.getPassword().isBlank()) {
                        existingUser.setPassword(passwordService.encode(user.getPassword()));
                    }
                    
                    return userRepository.save(existingUser);
                });
    }

    @Override
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @Override
    public boolean resetPassword(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String defaultPassword = passwordService.generateDefaultPassword();
            user.setPassword(passwordService.encode(defaultPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
    public Optional<User> resetPasswordAndGetUser(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    String defaultPassword = passwordService.generateDefaultPassword();
                    user.setPassword(passwordService.encode(defaultPassword));
                    return userRepository.save(user);
                });
    }

    @Override
    public List<UserSummary> getUserSummary() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserSummary(
                        user.getName(), 
                        user.getEmail(), 
                        user.getEnabled(), 
                        user.getRole().getName().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> toggleUserEnabled(Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setEnabled(!user.getEnabled());
                    return userRepository.save(user);
                });
    }
    
    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}