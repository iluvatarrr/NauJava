package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.nativeJava.impl.GoalCrudRepository;
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
    private final GoalCrudRepository goalCrudRepository;

    @Autowired
    public GoalServiceImpl(GoalCrudRepository goalCrudRepository) {
        this.goalCrudRepository = goalCrudRepository;
    }

    @Override
    public void save(Long id, String title) {
        var goal = new Goal(id,title);
        goalCrudRepository.save(goal);
    }

    @Override
    public List<Goal> findAll() {
        return goalCrudRepository.findAll();
    }

    @Override
    public Goal findById(Long id) {
        return goalCrudRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        goalCrudRepository.delete(id);
    }

    @Override
    public void updateGoalTitle(Long id, String goalTitle) {
        Goal user = findById(id);
        user.setTitle(goalTitle);
        goalCrudRepository.update(user);
    }
}