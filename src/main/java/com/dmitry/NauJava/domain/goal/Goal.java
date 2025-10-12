package com.dmitry.NauJava.domain.goal;

import com.dmitry.NauJava.domain.subGoal.SubGoal;
import java.time.LocalDateTime;
import java.util.List;

public class Goal {
    private Long id;
    private String title;
    private String description;
    private GoalStatus goalStatus;
    private GoalCategory goalCategory;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime deadline;
    private List<SubGoal> subGoalList;

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

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}