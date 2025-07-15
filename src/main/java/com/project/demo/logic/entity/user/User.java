package com.project.demo.logic.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.demo.logic.entity.achievement.UserAchievement;
import com.project.demo.logic.entity.auth.Role;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.game.*;
import com.project.demo.logic.entity.history.ActivityLog;
import com.project.demo.logic.entity.history.LoginHistory;
import com.project.demo.logic.entity.notification.Notification;
import com.project.demo.logic.entity.notification.Suggestion;
import com.project.demo.logic.entity.settings.Level;
import com.project.demo.logic.entity.settings.UserSettings;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Representa un usuario en el sistema.
 * Esta entidad mapea la tabla 'users' en la base de datos e implementa la interfaz
 * {@link UserDetails} para la integración con Spring Security.
 */
@Table(name = "users")
@Entity
public class User implements UserDetails {
    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del usuario.
     */
    private String name;
    /**
     * Correo electrónico del usuario. Debe ser único.
     */
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    /**
     * Contraseña del usuario. No puede ser nula.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rol asignado al usuario.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    /**
     * Nivel actual del usuario en el sistema.
     */
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(nullable = false)
    private boolean active = true;

    @JsonManagedReference("user-settings")
    @OneToOne
    @JoinColumn(name = "settings_id")
    private UserSettings settings;

    /**
     * Comentarios y valoraciones de juegos realizados por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GameFeedback> gameFeedbacks;

    /**
     * Sesiones de juego registradas por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GameSession> gameSessions;

    /**
     * Reportes de juegos enviados por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GameReport> gameReports;

    /**
     * Logros obtenidos por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserAchievement> achievements;

    /**
     * Sugerencias enviadas por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Suggestion> suggestions;

    /**
     * Notificaciones recibidas por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    /**
     * Cuidadores asociados a este usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserCaregiver> caregivers;

    /**
     * Juegos marcados como favoritos por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<FavoriteGame> favoriteGames;

    /**
     * Historial de inicios de sesión del usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<LoginHistory> loginHistories;

    /**
     * Rachas de juego del usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Streak> streaks;

    /**
     * Registro de actividades realizadas por el usuario.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ActivityLog> activityLogs;

    @Column(nullable = false)
    private boolean enabled = true;

    public User() {
    }

    public User(Long id, String name, String lastname, String email, String password, Role role, Level level, UserSettings settings, List<GameFeedback> gameFeedbacks, List<GameSession> gameSessions, List<GameReport> gameReports, List<UserAchievement> achievements, List<Suggestion> suggestions, List<Notification> notifications, List<UserCaregiver> caregivers, List<FavoriteGame> favoriteGames, List<LoginHistory> loginHistories, List<Streak> streaks, List<ActivityLog> activityLogs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.level = level;
        this.settings = settings;
        this.gameFeedbacks = gameFeedbacks;
        this.gameSessions = gameSessions;
        this.gameReports = gameReports;
        this.achievements = achievements;
        this.suggestions = suggestions;
        this.notifications = notifications;
        this.caregivers = caregivers;
        this.favoriteGames = favoriteGames;
        this.loginHistories = loginHistories;
        this.streaks = streaks;
        this.activityLogs = activityLogs;

    }

    /**
     * Devuelve las autorizaciones concedidas al usuario.
     * Utiliza el nombre del rol para crear una {@link SimpleGrantedAuthority}.
     * @return Una colección con la autoridad del rol del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve la contraseña utilizada para autenticar al usuario.
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public UserSettings getSettings() {
        return settings;
    }

    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    public List<GameFeedback> getGameFeedbacks() {
        return gameFeedbacks;
    }

    public void setGameFeedbacks(List<GameFeedback> gameFeedbacks) {
        this.gameFeedbacks = gameFeedbacks;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    public List<GameReport> getGameReports() {
        return gameReports;
    }

    public void setGameReports(List<GameReport> gameReports) {
        this.gameReports = gameReports;
    }

    public List<UserAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<UserAchievement> achievements) {
        this.achievements = achievements;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<UserCaregiver> getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(List<UserCaregiver> caregivers) {
        this.caregivers = caregivers;
    }

    public List<FavoriteGame> getFavoriteGames() {
        return favoriteGames;
    }

    public void setFavoriteGames(List<FavoriteGame> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    public List<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public void setLoginHistories(List<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    public List<Streak> getStreaks() {
        return streaks;
    }

    public void setStreaks(List<Streak> streaks) {
        this.streaks = streaks;
    }

    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    public boolean getEnabled() { return this.enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está bloqueado o no.
     * @return siempre {@code true}, indicando que la cuenta nunca está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado.
     * @return siempre {@code true}, indicando que las credenciales nunca expiran.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está habilitado o deshabilitado.
     * @return siempre {@code true}, indicando que el usuario siempre está habilitado.
     */
    @Override
    public boolean isEnabled() { return true; }

    /**
     * Devuelve el nombre de usuario utilizado para autenticar al usuario.
     * En este caso, es el correo electrónico.
     * @return el correo electrónico del usuario.
     */
    @Override
    public String getUsername() {
        return email;
    }
}
