package com.dmitry.NauJava.repository.nativeJava;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import java.util.List;

/**
 * Crud репозиторий с дженериками для гибкости
 */
public interface CrudRepository<T, ID> {
    void save(T entity);
    void update(T entity) throws ResourceNotFoundException;
    void delete(ID id) throws ResourceNotFoundException;
    T findById(ID id) throws ResourceNotFoundException;
    List<Goal> findAll();
}