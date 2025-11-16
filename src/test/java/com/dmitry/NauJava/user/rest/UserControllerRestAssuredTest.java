package com.dmitry.NauJava.user.rest;

import com.dmitry.NauJava.domain.user.Role;
import com.dmitry.NauJava.domain.user.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестовый класс для проверки API UserController.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8081")
class UserControllerRestAssuredTest {

    private static final String BASE_URL = "http://localhost:8081/api/v1/users";
    private static final String USERNAME = "s@s";
    private static final String PASSWORD = "ssss";

    /**
     * Дает в работе залогинений request.
     */
    private RequestSpecification givenAuth() {
        String sessionCookie = performFormLogin();
        return given()
                .cookie("JSESSIONID", sessionCookie)
                .contentType(ContentType.JSON);
    }

    /**
     * Логинится по сущности админа.
     */
    private String performFormLogin() {
        Response loginResponse = given()
                .contentType(ContentType.URLENC)
                .formParam("email", USERNAME)
                .formParam("password", PASSWORD)
                .redirects().follow(false)
                .when()
                .post("http://localhost:8081/perform_login");
        return loginResponse.getCookie("JSESSIONID");
    }

    /**
     * Тестирование успешного создания пользователя через API.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Создать DTO пользователя с email, ролями и паролем</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Отправить POST запрос на создание пользователя</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить статус код 200</li>
     *       <li>Проверить корректность email в ответе</li>
     *       <li>Проверить корректность ролей в ответе</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void createUserAPITest() {
        // Подготовка
        Map<String, Object> userDto = Map.of(
                "email", UUID.randomUUID() + "@mail.com",
                "roles", List.of("USER"),
                "password", PASSWORD
        );

        // Действия
        Response response = givenAuth()
                .contentType(ContentType.JSON)
                .body(userDto)
                .when()
                .post(BASE_URL);

        // Проверки
        assertEquals(200, response.getStatusCode());
        User user = response.as(User.class);
        assertEquals(user.getEmail(), userDto.get("email"));
        assertEquals(user.getRoles().stream().map(Enum::name).toList(), userDto.get("roles"));
    }

    /**
     * Тестирование создания пользователя с пустым набором ролей.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Создать DTO пользователя с пустым списком ролей</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Отправить POST запрос на создание пользователя</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить статус код 200</li>
     *       <li>Проверить корректность email в ответе</li>
     *       <li>Проверить, что пользователю назначена роль USER по умолчанию</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void createUserWithEmptyRolesAPITest() {
        // Подготовка
        Map<String, Object> userDto = Map.of(
                "email", UUID.randomUUID() + "@mail.com",
                "roles", List.of(),
                "password", PASSWORD
        );

        // Действия
        Response response = givenAuth()
                .contentType(ContentType.JSON)
                .body(userDto)
                .when()
                .post(BASE_URL);

        // Проверки
        assertEquals(200, response.getStatusCode());
        User responseUser = response.as(User.class);
        assertEquals(userDto.get("email"), responseUser.getEmail());
        assertEquals(responseUser.getRoles(), Set.of(Role.USER));
    }

    /**
     * Тестирование валидации при создании пользователя без email.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Создать DTO пользователя без поля email</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Отправить POST запрос на создание пользователя</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить статус код 400 (Bad Request)</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void createUserWithEmptyEmailAPITest() {
        // Подготовка
        Map<String, Object> userDto = Map.of(
                "roles", List.of("USER"),
                "password", PASSWORD
        );

        // Действия
        Response response = givenAuth()
                .contentType(ContentType.JSON)
                .body(userDto)
                .when()
                .post(BASE_URL);

        // Проверки
        assertEquals(400, response.getStatusCode());
    }

    /**
     * Тестирование валидации при создании пользователя без поля roles.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Создать DTO пользователя без поля roles</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Отправить POST запрос на создание пользователя</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить статус код 400 (Bad Request)</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void createUserWithNullRolesAPITest() {
        // Подготовка
        Map<String, Object> userDto = Map.of(
                "email", "no.roles@example.com",
                "password", PASSWORD
        );

        // Действия
        Response response = givenAuth()
                .contentType(ContentType.JSON)
                .body(userDto)
                .when()
                .post(BASE_URL);

        // Проверки
        assertEquals(400, response.getStatusCode());
    }

    /**
     * Тестирование валидации при создании пользователя без поля password.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Создать DTO пользователя без поля password</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Отправить POST запрос на создание пользователя</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить статус код 400 (Bad Request)</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    void createUserWithNullPasswordAPITest() {
        // Подготовка
        Map<String, Object> userDto = Map.of(
                "email", "no.roles@example.com",
                "roles", List.of("USER")
        );

        // Действия
        Response response = givenAuth()
                .contentType(ContentType.JSON)
                .body(userDto)
                .when()
                .post(BASE_URL);

        // Проверки
        assertEquals(400, response.getStatusCode());
    }
}