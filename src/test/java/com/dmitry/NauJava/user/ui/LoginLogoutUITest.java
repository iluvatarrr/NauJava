package com.dmitry.NauJava.user.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

/**
 * Тестовый класс для проверки логирования и логаута.
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginLogoutUITest {

    private WebDriver driver;
    @Value("${server.base.url}")
    private String BASE_URL;
    private String LOGIN_URL;
    private String PROFILE_URL;
    private final String EMAIL = "admin@admin";
    private final String PASSWORD = "admin";
    private final String EMAIL_FIELD = "email";
    private final String PASSWORD_FIELD = "password";

    @BeforeEach
    public void setUp() {
        LOGIN_URL = BASE_URL + "/api/v1/auth/login";
        PROFILE_URL = BASE_URL + "/api/v1/auth/me";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    /**
     * Тестирование успешного логина пользователя через UI.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Открыть страницу логина</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Ввести email в поле ввода</li>
     *       <li>Ввести пароль в поле ввода</li>
     *       <li>Отправить форму логина</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить, что произошел редирект на страницу профиля</li>
     *       <li>Проверить текущий URL</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    @Order(1)
    void testSuccessfulLogin() {
        // Подготовка
        driver.get(LOGIN_URL);

        // Действия
        driver.findElement(By.name(EMAIL_FIELD)).sendKeys(EMAIL);
        driver.findElement(By.name(PASSWORD_FIELD)).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("form")).submit();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(PROFILE_URL));

        // Проверки
        Assertions.assertEquals(PROFILE_URL, driver.getCurrentUrl());
    }

    /**
     * Тестирование успешного логаута пользователя через UI.
     *
     * <ol>
     *   <li><b>Подготовка</b>
     *     <ul>
     *       <li>Выполнить успешный логин</li>
     *     </ul>
     *   </li>
     *   <li><b>Действия</b>
     *     <ul>
     *       <li>Найти и нажать кнопку логаута</li>
     *       <li>Подтвердить выход из системы</li>
     *     </ul>
     *   </li>
     *   <li><b>Проверки</b>
     *     <ul>
     *       <li>Проверить, что произошел редирект на страницу логина</li>
     *       <li>Проверить текущий URL</li>
     *       <li>Проверить, что поля для ввода логина и пароля отображаются</li>
     *     </ul>
     *   </li>
     * </ol>
     */
    @Test
    @Order(2)
    void testLogout() {
        // Подготовка
        driver.get(LOGIN_URL);

        // Действия
        driver.findElement(By.name(EMAIL_FIELD)).sendKeys(EMAIL);
        driver.findElement(By.name(PASSWORD_FIELD)).sendKeys(PASSWORD);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(PROFILE_URL));
        driver.findElement(By.cssSelector("form[action='/logout'] button[type='submit']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(LOGIN_URL));

        // Проверки
        Assertions.assertEquals(LOGIN_URL, driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.name(EMAIL_FIELD)).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.name(PASSWORD_FIELD)).isDisplayed());
    }
}
