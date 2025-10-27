package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.domain.userProfile.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import java.util.Optional;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@RepositoryRestResource(path = "user-profiles", exported = false)
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    List<UserProfile> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("FROM UserProfile up JOIN User u ON u.id = up.user.id WHERE u.email = :email")
    Optional<UserProfile> findByEmail(@Param("email") String email);

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