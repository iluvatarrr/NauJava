package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.domain.userProfile.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    List<UserProfile> findByFirstNameAndLastName(String firstName, String lastName);

    default Optional<UserProfile> findByUserEmailHQL(String email, EntityManager entityManager) {
        String hql = "FROM UserProfile up JOIN User u ON u.id = up.user.id WHERE u.email = :email";
        TypedQuery<UserProfile> query = entityManager.createQuery(hql, UserProfile.class);
        query.setParameter("email", email);
        List<UserProfile> results =  query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    default List<UserProfile> findByFirstNameAndLastName(String firstName, String lastName, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
        Root<UserProfile> root = criteriaQuery.from(UserProfile.class);
        Predicate predicateFirstName = criteriaBuilder
                .equal(root.get("firstName"), firstName);
        Predicate predicateLastName = criteriaBuilder
                .equal(root.get("lastName"), lastName);
        Predicate finalPredicate = criteriaBuilder.and(predicateFirstName, predicateLastName);
        criteriaQuery.select(root).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    default Optional<UserProfile> findByEmail(String email, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserProfile> criteriaQuery = criteriaBuilder.createQuery(UserProfile.class);
        Root<UserProfile> root = criteriaQuery.from(UserProfile.class);
        Join<UserProfile, User> userJoin = root.join("user");
        Predicate predicateGoalTitle = criteriaBuilder.equal(userJoin.get("email"), email);
        criteriaQuery.select(root).where(predicateGoalTitle);
        List<UserProfile> results =  entityManager.createQuery(criteriaQuery).getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }
}