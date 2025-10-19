package com.dmitry.NauJava.repository.jpql;

import com.dmitry.NauJava.domain.goal.Goal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface GoalRepository extends CrudRepository<Goal, Long> {
    List<Goal> findByTitle(String title);
}