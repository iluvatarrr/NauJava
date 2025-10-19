package com.dmitry.NauJava;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.repository.jpql.GoalRepository;
import com.dmitry.NauJava.service.impl.TransactionalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Тестовый класс для проверки методов транцакционного сервиса.
 */
@SpringBootTest
public class TransactionalTest {
    private final TransactionalService transactionalService;
    private final GoalRepository goalRepository;

    @Autowired
    public TransactionalTest(final TransactionalService transactionalService, GoalRepository goalRepository) {
        this.transactionalService = transactionalService;
        this.goalRepository = goalRepository;
    }

    @Test
    @Transactional
    public void transactionalPosTest() {
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String titleSubGoal = UUID.randomUUID().toString();
        String descriptionSubGoal = UUID.randomUUID().toString();
        var goal = new Goal(title, description);
        var subGoal = new SubGoal(goal, titleSubGoal, descriptionSubGoal);
        var savedGoal = transactionalService.saveGoalWithSubGoals(goal, List.of(subGoal));
        var goalsFound = goalRepository.findByTitle(title);
        Assertions.assertNotNull(goalsFound);
        var goalFound = goalsFound.stream().findAny().get();
        Assertions.assertNotNull(goalFound);
        Assertions.assertTrue(goalsFound.stream().
                map(Goal::getId).anyMatch(id -> savedGoal.getId().equals(id)));
        Assertions.assertEquals(title, goalFound.getTitle());
        Assertions.assertEquals(description, goalFound.getDescription());
        var subGoalsFound = goalFound.getSubGoalList();
        Assertions.assertNotNull(subGoalsFound);
        Assertions.assertTrue(subGoalsFound.stream().
                map(SubGoal::getId).anyMatch(id -> savedGoal.getSubGoalList().get(0).getId().equals(id)));
        var subGoalFounded = subGoalsFound.get(0);
        Assertions.assertEquals(titleSubGoal, subGoalFounded.getTitle());
        Assertions.assertEquals(descriptionSubGoal, subGoalFounded.getDescription());
    }

    @Test
    public void transactionalNegTest() {
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String titleSubGoal = UUID.randomUUID().toString();
        String descriptionSubGoal = UUID.randomUUID().toString();
        var goal = new Goal(title, description);
        var subGoal = new SubGoal(goal, titleSubGoal, descriptionSubGoal);
        try {
            transactionalService.saveGoalWithSubGoalsWithException(goal, List.of(subGoal));
            Assertions.fail("Ожидалась ошибка");
        } catch (RuntimeException e) {
            Assertions.assertEquals("Транзакция откатилась", e.getMessage());
        }
        var goalsFound = goalRepository.findByTitle(title);
        Assertions.assertTrue(goalsFound.isEmpty(), "Цель должна была не сохраниться из-за роллбека");
    }
}