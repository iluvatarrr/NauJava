package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.impl.GoalRepository;
import com.dmitry.NauJava.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* Сервисный класс для работы с целями, взаимодействует со слоем репозитория Реализует CRUD функциональность
*/
@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public void save(Long id, String title) {
        var goal = new Goal(id,title);
        goalRepository.save(goal);
    }

    @Override
    public List<Goal> findAll() {
        return goalRepository.findAll();
    }

    @Override
    public Goal findById(Long id) {
        return goalRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        goalRepository.delete(id);
    }

    @Override
    public void updateGoalTitle(Long id, String goalTitle) {
        Goal user = findById(id);
        user.setTitle(goalTitle);
        goalRepository.update(user);
    }
}