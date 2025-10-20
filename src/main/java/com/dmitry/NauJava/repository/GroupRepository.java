package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.group.Group;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
    List<Group> findByOrganisationAndIsPublic(String organisation, boolean isPublic);

    default List<Group> findByTitleAndOrganisationHQL(String title, String organisation, EntityManager entityManager) {
        String hql = "FROM Group g WHERE g.title = :title AND g.organisation = :organisation";
        TypedQuery<Group> query = entityManager.createQuery(hql, Group.class);
        query.setParameter("title", title);
        query.setParameter("organisation", organisation);
        return query.getResultList();
    }

    default List<Group> findByOrganisationAndIsPublic(String organisation, boolean isPublic, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = criteriaQuery.from(Group.class);
        Predicate predicateOrganisation = criteriaBuilder
                .equal(root.get("organisation"), organisation);
        Predicate predicateIsPublic = criteriaBuilder
                .equal(root.get("isPublic"), isPublic);
        Predicate finalPredicate = criteriaBuilder.and(predicateOrganisation, predicateIsPublic);
        criteriaQuery.select(root).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    default List<Group> findByTitleAndOrganisation(String title, String organisation, EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = criteriaQuery.from(Group.class);
        Predicate predicateTitle = criteriaBuilder
                .equal(root.get("title"), title);
        Predicate predicateOrganisation = criteriaBuilder
                .equal(root.get("organisation"), organisation);
        Predicate finalPredicate = criteriaBuilder.and(predicateTitle, predicateOrganisation);
        criteriaQuery.select(root).where(finalPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}