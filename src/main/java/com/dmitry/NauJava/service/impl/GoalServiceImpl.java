package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.impl.GoalRepository;
import com.dmitry.NauJava.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public void create(Long id, String title) {
        var goal = new Goal();
        goal.setId(id);
        goal.setTitle(title);
        goalRepository.create(goal);
    }

    @Override
    public Goal findById(Long id) {
        return goalRepository.read(id);
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