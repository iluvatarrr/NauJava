package com.dmitry.NauJava.domain.user;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.group.Group;
import com.dmitry.NauJava.domain.userProfile.UserProfile;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Пользователь проекта
 * Содержит в себе почту и пароль для регистрации,
 * заложены возможности пререпроверки пароля и подтвержения регистрации
 * Есть связи с ролями для секьюрности
 * Пользователь может иметь список целей и относиться к ряду групп,
 * так же есть возможность более детально описать профиль
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private boolean isEnabled;
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "users_roles")
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<Goal> goals = new ArrayList<>();
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private UserProfile userProfile;
    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password,
                boolean isEnabled, Set<Role> roles, List<Goal> goals, UserProfile userProfile, Set<Group> groups) {
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.roles = roles;
        this.goals = goals;
        this.userProfile = userProfile;
        this.groups = groups;
    }

    public User(String email, Set<Role> roles) {
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> tasks) {
        this.goals = tasks;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}