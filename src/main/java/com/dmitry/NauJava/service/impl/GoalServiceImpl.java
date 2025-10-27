package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.repository.GoalRepository;
import com.dmitry.NauJava.repository.SubGoalRepository;
import com.dmitry.NauJava.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Сервисный класс для работы с целями, взаимодействует со слоем репозитория
 * Реализует CRUD функциональность
 */
@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;
    private final SubGoalRepository subGoalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, SubGoalRepository subGoalRepository) {
        this.goalRepository = goalRepository;
        this.subGoalRepository = subGoalRepository;
    }

    @Override
    public void save(Long id, String title) {
        var goal = new Goal(id,title);
        goalRepository.save(goal);
    }

    @Override
    public List<Goal> findAll() {
        return (List<Goal>) goalRepository.findAll();
    }

    @Override
    public Goal findById(Long id) {
        return goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
    }

    @Override
    public void deleteById(Long id) {
        goalRepository.deleteById(id);
    }

    @Override
    public void updateGoalTitle(Long id, String goalTitle) {
        Goal user = findById(id);
        user.setTitle(goalTitle);
        goalRepository.save(user);
    }

    @Transactional
    public Goal saveGoalWithSubGoals(Goal goal, List<SubGoal> subGoals) {
        goal.getSubGoalList().addAll(subGoals);
        Goal savedGoal = goalRepository.save(goal);
        for (SubGoal subGoal : subGoals) {
            subGoal.setGoal(savedGoal);
            subGoalRepository.save(subGoal);
        }
        return savedGoal;
    }

    @Transactional
    public void saveGoalWithSubGoalsWithException(Goal goal, List<SubGoal> subGoals) {
        goal.getSubGoalList().addAll(subGoals);
        Goal savedGoal = goalRepository.save(goal);
        for (SubGoal subGoal : subGoals) {
            subGoal.setGoal(savedGoal);
            subGoalRepository.save(subGoal);
        }
        throw new RuntimeException("Транзакция откатилась");
    }

    @Override
    public List<Goal> findByTitleOrderByCreatedAt(String title) {
        return goalRepository.findByTitleOrderByCreatedAt(title);
    }

    @Override
    public List<Goal> findByTitleAndDescription(String title, String description) {
        return goalRepository.findByTitleAndDescription(title, description);
    }
}