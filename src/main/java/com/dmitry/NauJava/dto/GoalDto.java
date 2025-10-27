package com.dmitry.NauJava.dto;

import com.dmitry.NauJava.domain.goal.GoalCategory;
import com.dmitry.NauJava.domain.goal.GoalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * Dto цели.
 */
public record GoalDto(String title,
                      String description,
                      GoalStatus goalStatus,
                      GoalCategory goalCategory,
                      @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
                      LocalDateTime createdAt) {
}