package com.dmitry.NauJava.repository.criteriaApi.impl;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.subGoal.SubGoal;
import com.dmitry.NauJava.repository.criteriaApi.SubGoalRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * SubGoalRepositoryCustomImpl - реализация кастомного репозитория для подцелей
 * Использует criteriaAPI, позволяет находить подцель по названию и описанию, а также
 * нахожить подцель по полю названия связной сущности (цели)
 */
@Repository
public class SubGoalRepositoryCustomImpl implements SubGoalRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public SubGoalRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<SubGoal> findByTitleAndDescription(String title, String description) {
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

    @Override
    public List<SubGoal> findByGoalTitle(String goalTitle) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SubGoal> criteriaQuery = criteriaBuilder.createQuery(SubGoal.class);
        Root<SubGoal> root = criteriaQuery.from(SubGoal.class);
        Join<SubGoal, Goal> goalJoin = root.join("goal");
        Predicate predicateGoalTitle = criteriaBuilder.equal(goalJoin.get("title"), goalTitle);
        criteriaQuery.select(root).where(predicateGoalTitle);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}