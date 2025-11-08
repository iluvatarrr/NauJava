package com.dmitry.NauJava.domain.goal;

import com.dmitry.NauJava.domain.group.Group;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Цель - основная единица проекта
 * Спроектирована, чтобы позже иметь доступ к данным описания цели,
 * статусе, времени создания и выполнения
 * дедлайне, также она содержит подцели, реализована OneToMany связь
 */
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;
    @Enumerated(EnumType.STRING)
    private GoalCategory goalCategory;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime completedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deadline;
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubGoal> subGoalList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "goals")
    private Set<Group> groups = new HashSet<>();

    public Goal() {}

    public Goal(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Goal(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Goal(Long id, String title, String description,
                GoalStatus goalStatus, GoalCategory goalCategory, LocalDateTime createdAt,
                LocalDateTime completedAt, LocalDateTime deadline, List<SubGoal> subGoalList, Set<Group> groups) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.goalStatus = goalStatus;
        this.goalCategory = goalCategory;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.deadline = deadline;
        this.subGoalList = subGoalList;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GoalStatus getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(GoalStatus goalStatus) {
        this.goalStatus = goalStatus;
    }

    public GoalCategory getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(GoalCategory goalCategory) {
        this.goalCategory = goalCategory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public List<SubGoal> getSubGoalList() {
        return subGoalList;
    }

    public void setSubGoalList(List<SubGoal> subGoalList) {
        this.subGoalList = subGoalList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("Цель:id:%s%n*title:%s%n*description:%s%n", id, title, description);
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}