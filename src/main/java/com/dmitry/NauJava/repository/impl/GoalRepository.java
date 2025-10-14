package com.dmitry.NauJava.repository.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
/**Слой для взаимодействия с данными
Есть возможность выполнить CRUD функции. при отутствии данных кидает кастомную ошибку
 **/
@Repository
public class GoalRepository implements CrudRepository<Goal, Long> {
    private final Map<Long,Goal> goalContainer;

    @Autowired
    public GoalRepository(Map<Long, Goal> goalContainer) {
        this.goalContainer = goalContainer;
    }

    @Override
    public void create(Goal entity) {
        goalContainer.put(entity.getId(), entity);
    }

    @Override
    public Goal read(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(goalContainer.get(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No goal with id %s found", id)));
    }

    @Override
    public void update(Goal entity) throws ResourceNotFoundException {
        read(entity.getId());
        goalContainer.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional.ofNullable(goalContainer.remove(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No goal with id %s found", id)));
    }
}