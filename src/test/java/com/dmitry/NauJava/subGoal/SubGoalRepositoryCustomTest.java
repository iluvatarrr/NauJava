package com.dmitry.NauJava.subGoal;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.repository.criteriaApi.SubGoalRepositoryCustom;
import com.dmitry.NauJava.repository.jpql.GoalRepository;
import com.dmitry.NauJava.repository.jpql.SubGoalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;

/**
 * Тестовый класс для проверки методов репозитория подцелей.
 */
@SpringBootTest
public class SubGoalRepositoryCustomTest {

    private final SubGoalRepositoryCustom subGoalRepositoryCustom;
    private final SubGoalRepository subGoalRepository;
    private final GoalRepository goalRepository;

    @Autowired
    public SubGoalRepositoryCustomTest(SubGoalRepositoryCustom subGoalRepositoryCustom, SubGoalRepository subGoalRepository, GoalRepository goalRepository) {
        this.subGoalRepositoryCustom = subGoalRepositoryCustom;
        this.subGoalRepository = subGoalRepository;
        this.goalRepository = goalRepository;
    }

    @Test
    public void findByTitleAndDescriptionTest() {
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String titleSubGoal = UUID.randomUUID().toString();
        String descriptionSubGoal = UUID.randomUUID().toString();
        var goal = new Goal(title, description);
        goalRepository.save(goal);
        var subGoal = new SubGoal(goal, titleSubGoal, descriptionSubGoal);
        subGoalRepository.save(subGoal);
        var subGoals = subGoalRepositoryCustom.findByTitleAndDescription(titleSubGoal, descriptionSubGoal);
        Assertions.assertNotNull(subGoals);
        var subGoalsFound = subGoals.stream().findAny().get();
        Assertions.assertNotNull(subGoalsFound);
        Assertions.assertTrue(subGoals.stream().
                map(SubGoal::getId).anyMatch(id -> subGoal.getId().equals(id)));
        Assertions.assertEquals(titleSubGoal, subGoalsFound.getTitle());
        Assertions.assertEquals(descriptionSubGoal, subGoalsFound.getDescription());
    }

    @Test
    public void findByGoalTitleTest() {
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String titleSubGoal = UUID.randomUUID().toString();
        String descriptionSubGoal = UUID.randomUUID().toString();
        var goal = new Goal(title, description);
        goalRepository.save(goal);
        var subGoal = new SubGoal(goal, titleSubGoal, descriptionSubGoal);
        subGoalRepository.save(subGoal);
        var subGoals = subGoalRepositoryCustom.findByGoalTitle(title);
        Assertions.assertNotNull(subGoals);
        var subGoalsFound = subGoals.stream().findAny().get();
        Assertions.assertNotNull(subGoalsFound);
        Assertions.assertTrue(subGoals.stream().
                map(SubGoal::getId).anyMatch(id -> subGoal.getId().equals(id)));
        Assertions.assertEquals(titleSubGoal, subGoalsFound.getTitle());
        Assertions.assertEquals(descriptionSubGoal, subGoalsFound.getDescription());
    }
}