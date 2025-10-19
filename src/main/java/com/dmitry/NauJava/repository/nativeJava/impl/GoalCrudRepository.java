package com.dmitry.NauJava.repository.nativeJava.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.nativeJava.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции. при отутствии данных кидает кастомную ошибку
 */
@Repository
public class GoalCrudRepository implements CrudRepository<Goal, Long> {
    private final Map<Long,Goal> goalContainer;

    @Autowired
    public GoalCrudRepository(Map<Long, Goal> goalContainer) {
        this.goalContainer = goalContainer;
    }

    @Override
    public List<Goal> findAll() {
        return goalContainer.values().stream().toList();
    }

    @Override
    public void save(Goal entity) {
        goalContainer.put(entity.getId(), entity);
    }

    @Override
    public Goal findById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(goalContainer.get(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No goal with id %s found", id)));
    }

    @Override
    public void update(Goal entity) throws ResourceNotFoundException {
        findById(entity.getId());
        goalContainer.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional.ofNullable(goalContainer.remove(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No goal with id %s found", id)));
    }
}