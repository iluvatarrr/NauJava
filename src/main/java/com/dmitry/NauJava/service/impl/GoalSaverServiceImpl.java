package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.repository.jpql.GoalRepository;
import com.dmitry.NauJava.repository.jpql.SubGoalRepository;
import com.dmitry.NauJava.service.GoalSaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Сервисный класс для работы с целями и подцелями, взаимодействует со слоями репозиторев
 * Реализует транцакционнаый метод сохранения цели.
 */
@Service
public class GoalSaverServiceImpl implements GoalSaverService {
    private final GoalRepository goalRepository;
    private final SubGoalRepository subGoalRepository;

    @Autowired
    public GoalSaverServiceImpl(GoalRepository goalRepository, SubGoalRepository subGoalRepository) {
        this.goalRepository = goalRepository;
        this.subGoalRepository = subGoalRepository;
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
}