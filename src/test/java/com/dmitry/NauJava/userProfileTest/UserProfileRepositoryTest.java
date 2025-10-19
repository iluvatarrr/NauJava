package com.dmitry.NauJava.userProfileTest;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.domain.userProfile.UserProfile;
import com.dmitry.NauJava.repository.jpql.UserProfileRepository;
import com.dmitry.NauJava.repository.jpql.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.UUID;

/**
 * Тестовый класс для проверки методов репозитория профилей пользователя.
 */
@SpringBootTest
public class UserProfileRepositoryTest {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserProfileRepositoryTest(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Test
    void findByFirstNameAndLastNameTest() {
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        var user = new User(email, password);
        userRepository.save(user);
        var userProfile = new UserProfile(user, firstName, lastName);
        userProfileRepository.save(userProfile);
        List<UserProfile> userProfileFoundedList = userProfileRepository.findByFirstNameAndLastName(firstName, lastName);
        Assertions.assertNotNull(userProfileFoundedList);
        var userProfileFound = userProfileFoundedList.stream().findAny().get();
        Assertions.assertNotNull(userProfileFound);
        Assertions.assertTrue(userProfileFoundedList.stream().
                map(UserProfile::getId).anyMatch(id -> user.getId().equals(id)));
        Assertions.assertEquals(firstName, userProfileFound.getFirstName());
        Assertions.assertEquals(lastName, userProfileFound.getLastName());
    }

    @Test
    void findByEmailTest() {
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        var user = new User(email, password);
        userRepository.save(user);
        var userProfile = new UserProfile(user, firstName, lastName);
        userProfileRepository.save(userProfile);
        UserProfile userProfileFounded = userProfileRepository.findByEmail(email);
        Assertions.assertNotNull(userProfileFounded);
        Assertions.assertEquals(firstName, userProfileFounded.getFirstName());
        Assertions.assertEquals(lastName, userProfileFounded.getLastName());
    }
}