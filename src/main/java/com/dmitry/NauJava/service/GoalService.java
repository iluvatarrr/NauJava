package com.dmitry.NauJava.service;

import com.dmitry.NauJava.domain.goal.Goal;

public interface GoalService {
    void create(Long id, String title);
    Goal findById(Long id);
    void deleteById(Long id);
    void updateGoalTitle(Long id, String goalTitle);
}