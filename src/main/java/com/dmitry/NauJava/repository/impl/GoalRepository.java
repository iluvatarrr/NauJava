package com.dmitry.NauJava.repository.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;

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
    public Goal read(Long id) {
        return Optional.ofNullable(goalContainer.get(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No goal with id %s found", id)));
    }

    @Override
    public void update(Goal entity) {
        goalContainer.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) {
        Optional.ofNullable(goalContainer.remove(id))
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("No goal with id %s found", id)));
    }
}