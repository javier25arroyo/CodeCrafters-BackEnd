package com.project.demo.logic.entity.auth;
import com.project.demo.logic.entity.achievement.UserAchievement;
import com.project.demo.logic.entity.caregiver.UserCaregiver;
import com.project.demo.logic.entity.game.*;
import com.project.demo.logic.entity.history.ActivityLog;
import com.project.demo.logic.entity.history.LoginHistory;
import com.project.demo.logic.entity.notification.Notification;
import com.project.demo.logic.entity.notification.Suggestion;
import com.project.demo.logic.entity.settings.Level;
import com.project.demo.logic.entity.settings.UserSettings;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @JsonManagedReference("user-settings")
    @OneToOne
    @JoinColumn(name = "settings_id")
    private UserSettings settings;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GameFeedback> gameFeedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GameSession> gameSessions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GameReport> gameReports;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserAchievement> achievements;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Suggestion> suggestions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserCaregiver> caregivers;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<FavoriteGame> favoriteGames;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<LoginHistory> loginHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Streak> streaks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ActivityLog> activityLogs;

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    
}
