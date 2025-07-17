package com.project.demo.logic.entity.auth;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.auth.RoleEnum;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.service.CloudinaryService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio para la autenticación de usuarios.
 * Gestiona el proceso de autenticación utilizando Spring Security.
 */
@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    
    private final GoogleAuthService googleAuthService;
    
    private final RoleRepository roleRepository;

    private final CloudinaryService cloudinaryService;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param userRepository        Repositorio para la gestión de usuarios.
     * @param authenticationManager Gestor de autenticación de Spring Security.
     * @param passwordEncoder       Codificador de contraseñas.
     * @param googleAuthService     Servicio para la autenticación con Google.
     * @param roleRepository        Repositorio para la gestión de roles.
     * @param cloudinaryService     Servicio para la carga de archivos a Cloudinary.
     */
    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            GoogleAuthService googleAuthService,
            RoleRepository roleRepository,
            CloudinaryService cloudinaryService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.googleAuthService = googleAuthService;
        this.roleRepository = roleRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public User signup(User input, MultipartFile file) throws IOException {
        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        if (file != null && !file.isEmpty()) {
            Map<?, ?> uploadResult = cloudinaryService.uploadFile(file);
            input.setProfileImageUrl(uploadResult.get("url").toString());
            input.setProfileImagePublicId(uploadResult.get("public_id").toString());
        }

        input.setPassword(passwordEncoder.encode(input.getPassword()));
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            throw new RuntimeException("Role not found");
        }
        input.setRole(optionalRole.get());
        return userRepository.save(input);
    }


    /**
     * Autentica a un usuario utilizando su correo electrónico y contraseña.
     *
     * @param input Objeto {@link User} que contiene el correo electrónico y la contraseña del usuario.
     * @return El objeto {@link User} autenticado.
     * @throws org.springframework.security.core.AuthenticationException Si la autenticación falla.
     */
    public User authenticate(User input) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

    public User authenticateWithGoogle(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = googleAuthService.verifyToken(idToken);
        
        String email = payload.getEmail();
        String googleId = payload.getSubject();
        String name = (String) payload.get("name");

        Optional<User> existingUser = userRepository.findByEmail(email);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getGoogleId() == null) {
                user.setGoogleId(googleId);
                userRepository.save(user);
            }
            return user;
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setGoogleId(googleId);
            newUser.setPassword(passwordEncoder.encode("GOOGLE_AUTH_" + googleId));
            
            Optional<Role> userRole = roleRepository.findByName(RoleEnum.USER);
            if (userRole.isPresent()) {
                newUser.setRole(userRole.get());
            } else {
                throw new RuntimeException("Default USER role not found");
            }
            
            return userRepository.save(newUser);
        }
    }
}
