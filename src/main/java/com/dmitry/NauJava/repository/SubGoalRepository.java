package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface SubGoalRepository extends CrudRepository<SubGoal, Long> {
    List<SubGoal> findByGoalTitle(String title);

    @Query("FROM SubGoal sg WHERE sg.title = :title AND sg.description = :description")
    List<SubGoal> findByTitleAndDescription(@Param("title") String title,  @Param("description") String description);

    default List<SubGoal> findByTitleAndDescription(String title, String description, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubGoal> criteriaQuery = criteriaBuilder.createQuery(SubGoal.class);
        Root<SubGoal> root = criteriaQuery.from(SubGoal.class);
        Predicate predicateTitle = criteriaBuilder
                .equal(root.get("title"), title);
        Predicate predicateDescription = criteriaBuilder
                .equal(root.get("description"), description);
        Predicate finalPredicate = criteriaBuilder.and(predicateTitle, predicateDescription);
        criteriaQuery.select(root).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    default List<SubGoal> findByGoalTitle(String goalTitle, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubGoal> criteriaQuery = criteriaBuilder.createQuery(SubGoal.class);
        Root<SubGoal> root = criteriaQuery.from(SubGoal.class);
        Join<SubGoal, Goal> goalJoin = root.join("goal");
        Predicate predicateGoalTitle = criteriaBuilder.equal(goalJoin.get("title"), goalTitle);
        criteriaQuery.select(root).where(predicateGoalTitle);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}