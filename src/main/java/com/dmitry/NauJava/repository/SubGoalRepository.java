package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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

    default List<SubGoal> findByTitleAndDescriptionHQL(String title, String description, EntityManager entityManager) {
        String hql = "FROM SubGoal sg WHERE sg.title = :title AND sg.description = :description";
        TypedQuery<SubGoal> query = entityManager.createQuery(hql, SubGoal.class);
        query.setParameter("title", title);
        query.setParameter("description", description);
        return query.getResultList();
    }

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