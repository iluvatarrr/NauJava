package com.dmitry.NauJava.domain.userProfile;

import com.dmitry.NauJava.domain.user.User;
import jakarta.persistence.*;

/**
 * Профиль пользователь проекта
 * Содержит ссылку на пользователя,
 * описывает его имя, фамилию, количество целей и выполненных целей.
 */
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private Long id;
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    private String firstName;
    private String lastName;
    private Integer countOfGoals;
    private Integer countOfDoneGoals;

    public UserProfile() {}

    public UserProfile(User user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserProfile(User user, String firstName,
                       String lastName, Integer countOfGoals, Integer countOfDoneGoals) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.countOfGoals = countOfGoals;
        this.countOfDoneGoals = countOfDoneGoals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getCountOfGoals() {
        return countOfGoals;
    }

    public void setCountOfGoals(Integer countOfGoals) {
        this.countOfGoals = countOfGoals;
    }

    public Integer getCountOfDoneGoals() {
        return countOfDoneGoals;
    }

    public void setCountOfDoneGoals(Integer countOfDoneGoals) {
        this.countOfDoneGoals = countOfDoneGoals;
    }

    public Long getId() {
        return id;
    }
}