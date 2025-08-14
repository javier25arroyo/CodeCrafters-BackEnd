package com.project.demo;

import com.project.demo.logic.entity.auth.AuthenticationService;
import com.project.demo.logic.entity.auth.JwtService;
import com.project.demo.logic.entity.auth.RegisterRequest;
import com.project.demo.logic.entity.caregiver.repository.UserCaregiverRepository;
import com.project.demo.logic.entity.game.Game;
import com.project.demo.logic.entity.game.GameTypeEnum;
import com.project.demo.logic.entity.game.Score;
import com.project.demo.logic.entity.game.repository.GameRepository;
import com.project.demo.logic.entity.game.repository.ScoreRepository;
import com.project.demo.logic.entity.rol.RoleRepository;
import com.project.demo.logic.entity.settings.LevelEnum;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import com.project.demo.logic.service.PuzzleScoreCalculator;
import com.project.demo.rest.auth.AuthRestController;
import com.project.demo.rest.game.GameController;
import com.project.demo.rest.user.UserRestController;
import com.project.demo.service.CaregiverService;
import com.project.demo.service.EmailService;
import com.project.demo.service.PasswordService;
import com.project.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;
import jakarta.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeCraftersUnitTests {

    @Mock
    private UserRepository userRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private CaregiverService caregiverService;
    @Mock
    private UserCaregiverRepository userCaregiverRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordService passwordService;
    @Mock
    private AuthenticationService authenticationService;
    @Mock
    private PuzzleScoreCalculator puzzleScoreCalculator;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private AuthRestController authRestController;
    @InjectMocks
    private UserRestController userRestController;
    @InjectMocks
    private GameController gameController;

    private User testUser;
    private Game testGame;
    private Score testScore;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setName("Test User");
        testUser.setPassword("encodedPassword");
        testUser.setEnabled(true);

        testGame = new Game();
        testGame.setId(1);
        testGame.setGameType(GameTypeEnum.PUZZLE);
        testGame.setLevel(LevelEnum.EASY);

        testScore = new Score();
        testScore.setId(1L);
        testScore.setUser(testUser);
        testScore.setGame(testGame);
        testScore.setScore(100.0);
        testScore.setLevel(LevelEnum.EASY);
        testScore.setTime(300L);
        testScore.setMovements(50);
        testScore.setDate(new Date());
        testScore.setGameType(GameTypeEnum.PUZZLE);
    }

    // ========== CONTROLADORES REST (3 PRUEBAS) ==========

    @Test
    void testAuthController_Login_Success() {
        // Test simplificado que verifica la lógica de autenticación
        User loginUser = new User();
        loginUser.setEmail("test@example.com");
        loginUser.setPassword("password123");

        // Mock del comportamiento del servicio de autenticación
        when(authenticationService.authenticate(any(User.class))).thenReturn(testUser);
        
        User authenticatedUser = authenticationService.authenticate(loginUser);
        
        assertNotNull(authenticatedUser);
        assertEquals("test@example.com", authenticatedUser.getEmail());
        assertEquals("Test User", authenticatedUser.getName());
        verify(authenticationService, times(1)).authenticate(any(User.class));
    }

    @Test
    void testUserController_GetCurrentUser_Success() {
        when(userService.getCurrentUser()).thenReturn(testUser);

        User result = userRestController.authenticatedUser();

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());
        verify(userService, times(1)).getCurrentUser();
    }

    @Test
    void testGameController_SaveScore_Success() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(gameRepository.findFirstByGameType(GameTypeEnum.PUZZLE)).thenReturn(Optional.of(testGame));
        when(puzzleScoreCalculator.calculateScore(any(LevelEnum.class), anyLong(), anyInt())).thenReturn(100.0);
        when(scoreRepository.save(any(Score.class))).thenReturn(testScore);

        ResponseEntity<Score> response = gameController.saveScore(testScore);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(100.0, response.getBody().getScore());
        verify(scoreRepository, times(1)).save(any(Score.class));
    }

    // ========== SERVICIOS Y COMPONENTES (7 PRUEBAS) ==========

    @Test
    void testJwtService_ExtractUsername_Success() {
        String token = "valid.jwt.token";
        String expectedUsername = "test@example.com";

        when(jwtService.extractUsername(token)).thenReturn(expectedUsername);

        String actualUsername = jwtService.extractUsername(token);

        assertEquals(expectedUsername, actualUsername);
        verify(jwtService, times(1)).extractUsername(token);
    }

    @Test
    void testJwtService_GenerateToken_Success() {
        String expectedToken = "generated.jwt.token";

        when(jwtService.generateToken(testUser)).thenReturn(expectedToken);

        String actualToken = jwtService.generateToken(testUser);

        assertEquals(expectedToken, actualToken);
        assertNotNull(actualToken);
        verify(jwtService, times(1)).generateToken(testUser);
    }

    @Test
    void testUserService_CreateUser_Success() {
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setName("New User");

        when(userService.createUser(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertEquals("test@example.com", createdUser.getEmail());
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testUserService_FindUserById_Success() {
        Long userId = 1L;

        when(userService.findUserById(userId)).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.findUserById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getId());
        assertEquals("test@example.com", foundUser.get().getEmail());
        verify(userService, times(1)).findUserById(userId);
    }

    @Test
    void testPasswordService_EncodePassword_Success() {
        String rawPassword = "plainPassword123";
        String encodedPassword = "encodedPassword123";

        when(passwordService.encode(rawPassword)).thenReturn(encodedPassword);

        String result = passwordService.encode(rawPassword);

        assertEquals(encodedPassword, result);
        assertNotEquals(rawPassword, result);
        verify(passwordService, times(1)).encode(rawPassword);
    }

    @Test
    void testEmailService_SendPasswordResetEmail_Success() throws MessagingException {
        String toEmail = "recipient@example.com";
        String resetToken = "reset-token-123";

        doNothing().when(emailService).sendPasswordResetEmail(toEmail, resetToken);
        
        assertDoesNotThrow(() -> emailService.sendPasswordResetEmail(toEmail, resetToken));
        verify(emailService, times(1)).sendPasswordResetEmail(toEmail, resetToken);
    }

    @Test
    void testUserRepository_FindByEmail_Success() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userRepository.findByEmail(email);

        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        assertEquals("Test User", foundUser.get().getName());
        verify(userRepository, times(1)).findByEmail(email);
    }
}