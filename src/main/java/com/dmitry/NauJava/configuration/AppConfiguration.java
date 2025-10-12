package com.dmitry.NauJava.configuration;

import com.dmitry.NauJava.domain.goal.Goal;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfiguration {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public Map<Long,Goal> goalContainer() {
        return new HashMap<>();
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