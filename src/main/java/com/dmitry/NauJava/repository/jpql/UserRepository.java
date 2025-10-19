package com.dmitry.NauJava.repository.jpql;

import com.dmitry.NauJava.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Слой для взаимодействия с данными
 * Есть возможность выполнить CRUD функции.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}