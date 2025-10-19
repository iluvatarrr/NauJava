package com.dmitry.NauJava.repository.jpql;

import com.dmitry.NauJava.domain.group.Group;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
}