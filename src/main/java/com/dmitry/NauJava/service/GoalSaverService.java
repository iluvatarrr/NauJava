package com.dmitry.NauJava.service;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import java.util.List;

/**
 * CRUD интерфейс для взаимодействия с целями
 */
public interface GoalSaverService {
    Goal saveGoalWithSubGoals(Goal goal, List<SubGoal> subGoals);
    void saveGoalWithSubGoalsWithException(Goal goal, List<SubGoal> subGoals);
}