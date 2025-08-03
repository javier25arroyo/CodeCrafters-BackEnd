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
import com.project.demo.logic.entity.settings.LevelEnum;
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
    @Column(nullable = false)
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
     * Id de la autenticacion de google. Puede ser nulo.
     */
    @Column(unique = true)
    private String googleId;

    /**
     * Indica si el usuario está habilitado en el sistema.
     */
    @Column(nullable = false)
    private Boolean enabled = true;

    /**
     * Rol asignado al usuario.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    /**
     * Nivel actual del usuario en el sistema.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private LevelEnum level;

    /**
     * Configuración personalizada del usuario.
     */
    @JsonManagedReference("user-settings")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "settings_id", referencedColumnName = "id")
    private UserSettings settings;

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

    /**
     * Constructor por defecto.
     */
    public User() {
    }

    /**
     * Constructor con todos los parámetros para inicializar un objeto User.
     * @param id Identificador único.
     * @param name Nombre del usuario.
     * @param email Correo electrónico.
     * @param password Contraseña.
     * @param googleId Id de autenticación de Google.
     * @param role Rol del usuario.
     * @param level Nivel del usuario.
     * @param settings Configuración del usuario.
     * @param gameSessions Sesiones de juego.
     * @param gameReports Reportes de juegos.
     * @param achievements Logros del usuario.
     * @param suggestions Sugerencias del usuario.
     * @param notifications Notificaciones del usuario.
     * @param caregivers Cuidadores asociados.
     * @param loginHistories Historial de inicio de sesión.
     * @param streaks Rachas de juego.
     * @param activityLogs Registro de actividades.
     */

    public User(Long id, String name, String email, String password, String googleId, Role role, LevelEnum level, UserSettings settings, List<GameSession> gameSessions, List<GameReport> gameReports, List<UserAchievement> achievements, List<Suggestion> suggestions, List<Notification> notifications, List<UserCaregiver> caregivers, List<LoginHistory> loginHistories, List<Streak> streaks, List<ActivityLog> activityLogs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.googleId = googleId;
        this.role = role;
        this.level = level;
        this.settings = settings;
        this.gameSessions = gameSessions;
        this.gameReports = gameReports;
        this.achievements = achievements;
        this.suggestions = suggestions;
        this.notifications = notifications;
        this.caregivers = caregivers;
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

    /**
     * Obtiene el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     *
     * @param id El nuevo ID del usuario.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param name El nuevo nombre del usuario.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El nuevo correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el ID de Google del usuario.
     *
     * @return El ID de Google del usuario.
     */
    public String getGoogleId() {
        return googleId;
    }

    /**
     * Establece el ID de Google del usuario.
     *
     * @param googleId El nuevo ID de Google del usuario.
     */
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    /**
     * Indica si el usuario está habilitado.
     *
     * @return True si el usuario está habilitado, false en caso contrario.
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Establece si el usuario está habilitado.
     *
     * @param enabled El nuevo estado de habilitación del usuario.
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Devuelve la contraseña utilizada para autenticar al usuario.
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La nueva contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param role El nuevo rol del usuario.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Obtiene el nivel del usuario.
     *
     * @return El nivel del usuario.
     */
    public LevelEnum getLevel() {
        return level;
    }

    /**
     * Establece el nivel del usuario.
     *
     * @param level El nuevo nivel del usuario.
     */
    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    /**
     * Obtiene la configuración del usuario.
     *
     * @return La configuración del usuario.
     */
    public UserSettings getSettings() {
        return settings;
    }

    /**
     * Establece la configuración del usuario.
     *
     * @param settings La nueva configuración del usuario.
     */
    public void setSettings(UserSettings settings) {
        this.settings = settings;
    }

    /**
     * Obtiene las sesiones de juego del usuario.
     *
     * @return Las sesiones de juego del usuario.
     */
    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    /**
     * Establece las sesiones de juego del usuario.
     *
     * @param gameSessions Las nuevas sesiones de juego del usuario.
     */
    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }

    /**
     * Obtiene los reportes de juego del usuario.
     *
     * @return Los reportes de juego del usuario.
     */
    public List<GameReport> getGameReports() {
        return gameReports;
    }

    /**
     * Establece los reportes de juego del usuario.
     *
     * @param gameReports Los nuevos reportes de juego del usuario.
     */
    public void setGameReports(List<GameReport> gameReports) {
        this.gameReports = gameReports;
    }

    /**
     * Obtiene los logros del usuario.
     *
     * @return Los logros del usuario.
     */
    public List<UserAchievement> getAchievements() {
        return achievements;
    }

    /**
     * Establece los logros del usuario.
     *
     * @param achievements Los nuevos logros del usuario.
     */
    public void setAchievements(List<UserAchievement> achievements) {
        this.achievements = achievements;
    }

    /**
     * Obtiene las sugerencias del usuario.
     *
     * @return Las sugerencias del usuario.
     */
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    /**
     * Establece las sugerencias del usuario.
     *
     * @param suggestions Las nuevas sugerencias del usuario.
     */
    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    /**
     * Obtiene las notificaciones del usuario.
     *
     * @return Las notificaciones del usuario.
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Establece las notificaciones del usuario.
     *
     * @param notifications Las nuevas notificaciones del usuario.
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Obtiene los cuidadores asociados a este usuario.
     *
     * @return Los cuidadores asociados a este usuario.
     */
    public List<UserCaregiver> getCaregivers() {
        return caregivers;
    }

    /**
     * Establece los cuidadores asociados a este usuario.
     *
     * @param caregivers Los nuevos cuidadores asociados a este usuario.
     */
    public void setCaregivers(List<UserCaregiver> caregivers) {
        this.caregivers = caregivers;
    }


    /**
     * Obtiene el historial de inicios de sesión del usuario.
     *
     * @return El historial de inicios de sesión del usuario.
     */
    public List<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    /**
     * Establece el historial de inicios de sesión del usuario.
     *
     * @param loginHistories El nuevo historial de inicios de sesión del usuario.
     */
    public void setLoginHistories(List<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    /**
     * Obtiene las rachas de juego del usuario.
     *
     * @return Las rachas de juego del usuario.
     */
    public List<Streak> getStreaks() {
        return streaks;
    }

    /**
     * Establece las rachas de juego del usuario.
     *
     * @param streaks Las nuevas rachas de juego del usuario.
     */
    public void setStreaks(List<Streak> streaks) {
        this.streaks = streaks;
    }

    /**
     * Obtiene el registro de actividades realizadas por el usuario.
     *
     * @return El registro de actividades realizadas por el usuario.
     */
    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }

    /**
     * Establece el registro de actividades realizadas por el usuario.
     *
     * @param activityLogs El nuevo registro de actividades realizadas por el usuario.
     */
    public void setActivityLogs(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     * @return siempre {@code true}, indicando que la cuenta nunca expira.
     */
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
    public boolean isEnabled() {
        return true;
    }

    /**
     * Devuelve el nombre de usuario utilizado para autenticar al usuario.
     * En este caso, es el correo electrónico.
     * @return el correo electrónico del usuario.
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                ", googleId='" + googleId + '\'' +
                ", role=" + role +
                ", level=" + level +
                ", settings=" + settings +
                '}';
    }
}
