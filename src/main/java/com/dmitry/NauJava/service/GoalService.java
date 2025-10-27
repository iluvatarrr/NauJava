package com.dmitry.NauJava.service;

import com.dmitry.NauJava.domain.goal.Goal;
import java.util.List;

/**
 * CRUD интерфейс для взаимодействия с целями
 */
public interface GoalService {
    void save(Long id, String title);
    void deleteById(Long id);
    void updateGoalTitle(Long id, String goalTitle);
    Goal findById(Long id);
    List<Goal> findAll();
    List<Goal> findByTitleOrderByCreatedAt(String title);
    List<Goal> findByTitleAndDescription(String title, String description);
}