package com.dmitry.NauJava.user.unit;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.user.User;
import com.dmitry.NauJava.repository.UserRepository;
import com.dmitry.NauJava.service.UserService;
import com.dmitry.NauJava.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для проверки методов UserServiceTest.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    /**
     * Тестирование корректного сохранения пользователя с закодированным паролем.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Создать пользователя с email и паролем</li>
     *       <li>Настроить мок passwordEncoder для возврата закодированного пароля</li>
     *       <li>Настроить мок userRepository для возврата сохраненного пользователя</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Вызвать метод save сервиса</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить, что возвращенный пользователь не null</li>
     *       <li>Проверить корректность email</li>
     *       <li>Проверить, что пароль закодирован</li>
     *       <li>Проверить, что пользователь отключен</li>
     *       <li>Проверить вызовы зависимостей</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testSaveValidUserWithEncodedPassword() {
        // Подготовка
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plainPassword");

        String encodedPassword = "encodedPassword";
        User savedUser = new User();
        savedUser.setEmail("test@example.com");
        savedUser.setPassword(encodedPassword);
        savedUser.setEnabled(false);

        when(passwordEncoder.encode("plainPassword")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Действия
        User result = userService.save(user);

        // Проверки
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertFalse(result.isEnabled());

        verify(passwordEncoder).encode("plainPassword");
        verify(userRepository).save(user);
    }

    /**
     * Тестирование выбрасывания ошибки при сохранении null пользователя.
     *
     * <ol>
     *   <li><b>Действия и проверки</b>
     *     <ul>
     *       <li>Вызвать метод save с null пользователем</li>
     *       <li>Проверить, что выбрасывается NullPointerException</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testSaveWithNullUser() {
        assertThrows(NullPointerException.class, () -> userService.save(null));
    }


    /**
     * Тестирование поиска пользователя по email.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Настроить мок userRepository для возврата пользователя по email</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Вызвать метод findByEmail с существующим email</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить, что возвращенный пользователь не null</li>
     *       <li>Проверить корректность email</li>
     *       <li>Проверить вызов репозитория</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testFindByEmail() {
        // Подготовка
        String email = "existing@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Действия
        User result = userService.findByEmail(email);

        // Проверки
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
    }

    /**
     * Тестирование выбрасывания ошибки при поиске по несуществующему email.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Настроить мок userRepository для возврата empty Optional</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия и проверки</b>
     *     <ul>
     *       <li>Вызвать метод findByEmail с несуществующим email</li>
     *       <li>Проверить, что выбрасывается ResourceNotFoundException</li>
     *       <li>Проверить корректность сообщения об ошибке</li>
     *       <li>Проверить вызов репозитория</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testFindByEmailWithNonExistingEmail() {
        // Подготовка
        String nonExistingEmail = "nonexisting@example.com";

        when(userRepository.findByEmail(nonExistingEmail)).thenReturn(Optional.empty());

        // Действия и Проверки
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findByEmail(nonExistingEmail)
        );

        // Проверки
        assertEquals(String.format("User with email: %s, not found", nonExistingEmail),
                exception.getMessage());
        verify(userRepository).findByEmail(nonExistingEmail);
    }

    /**
     * Тестирование выбрасывания ошибки при поиске по null email.
     *
     * <ol>
     *   <li><b>Действия и проверки</b>
     *     <ul>
     *       <li>Вызвать метод findByEmail с null</li>
     *       <li>Проверить, что выбрасывается ResourceNotFoundException</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testFindByEmailWithNullEmail() {
        assertThrows(ResourceNotFoundException.class, () -> userService.findByEmail(null));
    }

    /**
     * Тестирование выбрасывания ошибки при поиске по пустому email.
     *
     * <ol>
     *   <li><b>Действия и проверки</b>
     *     <ul>
     *       <li>Вызвать метод findByEmail с пустой строкой</li>
     *       <li>Проверить, что выбрасывается ResourceNotFoundException</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testFindByEmailWithEmptyEmail() {
        assertThrows(ResourceNotFoundException.class, () -> userService.findByEmail(""));
    }

    /**
     * Тестирование подсчета количества пользователей.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Настроить мок userRepository для возврата заданного количества</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Вызвать метод count</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить корректность возвращаемого количества</li>
     *       <li>Проверить вызов репозитория</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void testCountUsers() {
        // Подготовка
        long expectedCount = 5L;
        when(userRepository.count()).thenReturn(expectedCount);

        // Действия
        Long result = userService.count();

        // Проверки
        assertEquals(expectedCount, result);
        verify(userRepository).count();
    }
}
