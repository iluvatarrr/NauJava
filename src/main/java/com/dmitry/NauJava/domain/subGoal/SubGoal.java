package com.dmitry.NauJava.domain.subGoal;

import com.dmitry.NauJava.domain.goal.GoalStatus;
import java.time.LocalDateTime;
/**
* Подцель - необходима для реализации поэтапного выполнения цели
* Содержит все поля, что и у цели, без указания типа
*/
public class SubGoal {
    private Long id;
    private String title;
    private String description;
    private GoalStatus goalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime deadline;

    public SubGoal() {}

    public SubGoal(Long id, String title, String description,
                   GoalStatus goalStatus, LocalDateTime createdAt,
                   LocalDateTime completedAt, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.goalStatus = goalStatus;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}