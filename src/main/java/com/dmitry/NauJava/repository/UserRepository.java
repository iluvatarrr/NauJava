package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmailAndIsEnabled(String email, boolean isEnabled);

    default List<User> findByRoleHQL(Role role, EntityManager entityManager) {
        String hql = "FROM User u JOIN u.roles r WHERE r = :role";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

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