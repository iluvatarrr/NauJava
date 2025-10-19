package com.dmitry.NauJava.repository.jpql;

import com.dmitry.NauJava.domain.subGoal.SubGoal;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface SubGoalRepository extends CrudRepository<SubGoal, Long> {
}