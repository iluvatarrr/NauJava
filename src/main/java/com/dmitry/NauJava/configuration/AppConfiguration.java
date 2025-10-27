package com.dmitry.NauJava.configuration;

import com.dmitry.NauJava.domain.goal.Goal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * Общий класс конфигурации приложения.
 * В нем регистрируются бины, необходимые для его работы,
 * например, контейнер для целей, классы для работы с деревом json
 * Также тут есть поля со значениями из конфигурации
 */
@Configuration
public class AppConfiguration {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public Map<Long,Goal> goalContainer() {
        return new HashMap<>();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @DependsOn("goalServiceImpl")
    @PostConstruct
    public void getValues() {
        System.out.println(appName);
        System.out.println("---");
        System.out.println(appVersion);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Goal API")
                        .description("Goal Management API")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Documentation")
                        .url("https://springdoc.org"));
    }
}