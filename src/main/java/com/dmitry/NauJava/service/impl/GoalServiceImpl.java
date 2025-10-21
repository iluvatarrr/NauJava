package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.GoalRepository;
import com.dmitry.NauJava.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Сервисный класс для работы с целями, взаимодействует со слоем репозитория
 * Реализует CRUD функциональность
 */
@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalCrudRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalCrudRepository = goalRepository;
    }

    @Override
    public void save(Long id, String title) {
        var goal = new Goal(id,title);
        goalCrudRepository.save(goal);
    }

    @Override
    public List<Goal> findAll() {
        return (List<Goal>) goalCrudRepository.findAll();
    }

    @Override
    public Goal findById(Long id) {
        return goalCrudRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
    }

    @Override
    public void deleteById(Long id) {
        goalCrudRepository.deleteById(id);
    }

    @Override
    public void updateGoalTitle(Long id, String goalTitle) {
        Goal user = findById(id);
        user.setTitle(goalTitle);
        goalCrudRepository.save(user);
    }
}