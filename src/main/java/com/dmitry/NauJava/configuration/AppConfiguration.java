package com.dmitry.NauJava.configuration;

import com.dmitry.NauJava.domain.goal.Goal;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
/**
 * Общий класс конфигурации приложения.
 * В нем регистрируются бины, необходимые для его работы,
 * например, контейнер для целей, классы для работы с деревом json
 * Также тут есть поля со значениями из конфигурации
 * **/
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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
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
}