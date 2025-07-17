package com.project.demo.service;

import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User updateUser(Long userId, User user, MultipartFile file) throws IOException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (file != null && !file.isEmpty()) {
            if (existingUser.getProfileImagePublicId() != null && !existingUser.getProfileImagePublicId().isEmpty()) {
                cloudinaryService.deleteFile(existingUser.getProfileImagePublicId());
            }
            Map<?, ?> uploadResult = cloudinaryService.uploadFile(file);
            existingUser.setProfileImageUrl(uploadResult.get("url").toString());
            existingUser.setProfileImagePublicId(uploadResult.get("public_id").toString());
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }
}
