package com.dmitry.NauJava.domain.subGoal;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.goal.GoalStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Подцель - необходима для реализации поэтапного выполнения цели
 * Содержит все поля, что и у цели, без указания типа
 */
@Entity
@Table(name = "sub_goals")
public class SubGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime deadline;
    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    public SubGoal() {}

    public SubGoal(Goal goal,String title, String description) {
        this.title = title;
        this.goal = goal;
        this.description = description;
    }

    public SubGoal(String title, String description,
                   GoalStatus goalStatus, LocalDateTime createdAt,
                   LocalDateTime completedAt, LocalDateTime deadline, Goal goal) {
        this.title = title;
        this.description = description;
        this.goalStatus = goalStatus;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.deadline = deadline;
        this.goal = goal;
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

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}