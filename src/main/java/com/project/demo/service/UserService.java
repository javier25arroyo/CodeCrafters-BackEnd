package com.project.demo.service;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> getAllUsers(Pageable pageable);
    User createUser(User user);
    Optional<User> updateUser(Long userId, User user);
    boolean deleteUser(Long userId);
    User getCurrentUser();
    boolean resetPassword(Long userId);
    Optional<User> resetPasswordAndGetUser(Long userId);
    List<UserSummary> getUserSummary();
    Optional<User> toggleUserEnabled(Long userId);
    Optional<User> findUserById(Long userId);
}