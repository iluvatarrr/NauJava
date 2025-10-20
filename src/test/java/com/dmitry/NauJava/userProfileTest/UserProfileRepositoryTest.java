package com.dmitry.NauJava.userProfileTest;

import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.domain.userProfile.UserProfile;
import com.dmitry.NauJava.repository.jpql.UserProfileRepository;
import com.dmitry.NauJava.repository.jpql.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Тестовый класс для проверки методов репозитория профилей пользователя.
 */
@SpringBootTest
@Transactional // Добавить транзакционность
public class UserProfileRepositoryTest {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserProfileRepositoryTest(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    /**
     * Тест поиска профиля по имени и фамилии.
     */
    @Test
    void findByFirstNameAndLastNameTest() {
        // Подготовка
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        // Действия
        var user = new User(email, password);
        userRepository.save(user); // Присваиваем сохраненную сущность
        var userProfile = new UserProfile(user, firstName, lastName);
        userProfileRepository.save(userProfile);
        List<UserProfile> userProfileFoundedList = userProfileRepository.findByFirstNameAndLastName(firstName, lastName);

        // Проверки
        Assertions.assertNotNull(userProfileFoundedList);
        Assertions.assertFalse(userProfileFoundedList.isEmpty());
        var userProfileFound = userProfileFoundedList.getFirst();
        Assertions.assertNotNull(userProfileFound);
        Assertions.assertEquals(user.getId(), userProfileFound.getId());
        Assertions.assertEquals(firstName, userProfileFound.getFirstName());
        Assertions.assertEquals(lastName, userProfileFound.getLastName());
    }

    /**
     * Тест проверки поиска профиля по email пользователя.
     */
    @Test
    void findByEmailTest() {
        // Подготовка
        String firstName = UUID.randomUUID().toString();
        String lastName = UUID.randomUUID().toString();
        String email = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        // Действия
        var user = new User(email, password);
        userRepository.save(user);
        var userProfile = new UserProfile(user, firstName, lastName);
        userProfileRepository.save(userProfile);
        UserProfile userProfileFounded = userProfileRepository.findByEmail(email);

        // Проверки
        Assertions.assertNotNull(userProfileFounded);
        Assertions.assertEquals(firstName, userProfileFounded.getFirstName());
        Assertions.assertEquals(lastName, userProfileFounded.getLastName());
        Assertions.assertEquals(user.getId(), userProfileFounded.getId());
    }
}