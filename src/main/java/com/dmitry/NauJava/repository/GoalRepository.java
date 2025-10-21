package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.goal.Goal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface GoalRepository extends CrudRepository<Goal, Long> {
    List<Goal> findByTitle(String title);

    List<Goal> findByTitleOrderByCreatedAt(String title);

    @Query("FROM Goal g WHERE g.title = :title AND g.description = :description")
    List<Goal> findByTitleAndDescription(@Param("title") String title, @Param("description") String description);

    default List<Goal> findByTitleAndDescription(String title, String description, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Goal> criteriaQuery = criteriaBuilder.createQuery(Goal.class);
        Root<Goal> root = criteriaQuery.from(Goal.class);
        Predicate predicateTitle = criteriaBuilder
                .equal(root.get("title"), title);
        Predicate predicateDescription = criteriaBuilder
                .equal(root.get("description"), description);
        Predicate finalPredicate = criteriaBuilder.and(predicateTitle, predicateDescription);
        criteriaQuery.select(root).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    default List<Goal> findByTitleOrderByCreatedAt(String title, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Goal> criteriaQuery = criteriaBuilder.createQuery(Goal.class);
        Root<Goal> root = criteriaQuery.from(Goal.class);
        Predicate predicateGoalTitle = criteriaBuilder.equal(root.get("title"), title);
        criteriaQuery.select(root).where(predicateGoalTitle);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createdAt")));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}