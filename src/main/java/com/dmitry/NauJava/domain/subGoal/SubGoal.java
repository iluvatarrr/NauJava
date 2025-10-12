package com.dmitry.NauJava.domain.subGoal;

import com.dmitry.NauJava.domain.goal.GoalStatus;
import java.time.LocalDateTime;

public class SubGoal {
    private Long id;
    private String title;
    private String description;
    private GoalStatus goalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime deadline;
}