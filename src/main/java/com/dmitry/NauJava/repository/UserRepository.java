package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

/**
 * Слой для взаимодействия с данными
 * Реализует JPA репозиторий.
 */
@RepositoryRestResource(path = "users", exported = false)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndIsEnabled(String email, boolean isEnabled);
    Optional<User> findByEmail(String email);

    @Query("FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findByRole(@Param("role") Role role);

    default List<User> findByRole(Role role, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Join<User, Role> rolesJoin = root.join("roles");
        Predicate predicate = criteriaBuilder.equal(rolesJoin, role);
        criteriaQuery.select(root).where(predicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    default Optional<User> findByEmailAndIsEnabled(String email, boolean isEnabled, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        Predicate predicateEmail = criteriaBuilder
                .equal(root.get("email"), email);
        Predicate predicateIsEnabled = criteriaBuilder
                .equal(root.get("isEnabled"), isEnabled);
        Predicate finalPredicate = criteriaBuilder.and(predicateEmail, predicateIsEnabled);
        criteriaQuery.select(root).where(finalPredicate);
        List<User> results = entityManager.createQuery(criteriaQuery).getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }
}