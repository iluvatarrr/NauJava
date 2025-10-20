package com.dmitry.NauJava.goal;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.repository.jpql.GoalRepository;
import com.dmitry.NauJava.service.impl.GoalSaverServiceImpl;
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
public class GoalSaverServiceTest {
    private final GoalSaverServiceImpl transactionalServiceImpl;
    private final GoalRepository goalRepository;

    @Autowired
    public GoalSaverServiceTest(final GoalSaverServiceImpl transactionalServiceImpl, GoalRepository goalRepository) {
        this.transactionalServiceImpl = transactionalServiceImpl;
        this.goalRepository = goalRepository;
    }

    /**
     * Тест сохраниения задачи и подзадач атомарно.
     */
    @Test
    @Transactional
    public void transactionalPosTest() {
        // Подготовка
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String titleSubGoal = UUID.randomUUID().toString();
        String descriptionSubGoal = UUID.randomUUID().toString();

        // Действия
        var goal = new Goal(title, description);
        var subGoal = new SubGoal(goal, titleSubGoal, descriptionSubGoal);
        var savedGoal = transactionalServiceImpl.saveGoalWithSubGoals(goal, List.of(subGoal));
        var goalsFound = goalRepository.findByTitle(title);

        // Проверки
        Assertions.assertNotNull(goalsFound);
        var goalFound = goalsFound.stream().findAny().orElseThrow(() ->
                new AssertionError("SubGoal not found"));
        Assertions.assertNotNull(goalFound);
        Assertions.assertTrue(goalsFound.stream().
                map(Goal::getId).anyMatch(id -> savedGoal.getId().equals(id)));
        Assertions.assertEquals(title, goalFound.getTitle());
        Assertions.assertEquals(description, goalFound.getDescription());
        var subGoalsFound = goalFound.getSubGoalList();
        Assertions.assertNotNull(subGoalsFound);
        Assertions.assertTrue(subGoalsFound.stream().
                map(SubGoal::getId).anyMatch(id -> savedGoal.getSubGoalList().getFirst().getId().equals(id)));
        var subGoalFounded = subGoalsFound.getFirst();
        Assertions.assertEquals(titleSubGoal, subGoalFounded.getTitle());
        Assertions.assertEquals(descriptionSubGoal, subGoalFounded.getDescription());
    }

    /**
     * Тест провального сохраниения задачи и подзадач атомарно.
     */
    @Test
    public void transactionalNegTest() {
        // Подготовка
        String title = UUID.randomUUID().toString();
        String description = UUID.randomUUID().toString();
        String titleSubGoal = UUID.randomUUID().toString();
        String descriptionSubGoal = UUID.randomUUID().toString();

        // Действия
        var goal = new Goal(title, description);
        var subGoal = new SubGoal(goal, titleSubGoal, descriptionSubGoal);

        // Проверки
        try {
            transactionalServiceImpl.saveGoalWithSubGoalsWithException(goal, List.of(subGoal));
            Assertions.fail("Ожидалась ошибка");
        } catch (RuntimeException e) {
            Assertions.assertEquals("Транзакция откатилась", e.getMessage());
        }
        var goalsFound = goalRepository.findByTitle(title);
        Assertions.assertTrue(goalsFound.isEmpty(), "Цель должна была не сохраниться из-за роллбека");
    }
}