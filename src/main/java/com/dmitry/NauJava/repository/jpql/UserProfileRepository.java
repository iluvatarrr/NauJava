package com.dmitry.NauJava.repository.jpql;

import com.dmitry.NauJava.domain.userProfile.UserProfile;
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
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    List<UserProfile> findByFirstNameAndLastName(String firstName, String lastName);
    @Query(value = """
        SELECT up.*
            FROM user_profile up
            JOIN users u
                ON u.id = up.user_id
            WHERE u.email = :email
    """, nativeQuery = true)
    UserProfile findByEmail(@Param("email") String email);
}