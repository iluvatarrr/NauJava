package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.service.ActuatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
/**Серсис для работы с аоп
Реализует возможность обращаться
к актуатору для вытаскивания метрик, представляя их в json формате
Защищен от неверного обращения к url
**/
@Service
public class ActuatorServiceImpl implements ActuatorService {
    @Value("${app.actuator-base-url}")
    private String ACTUATOR_BASE_URL;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public ActuatorServiceImpl(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public void showAvailableMetrics() {
        try {
            String url = String.format("%s/metrics", ACTUATOR_BASE_URL);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode names = root.path("names");
            System.out.println("Доступные метрики:");
            names.forEach(metric -> System.out.printf(" - %s%n", metric.asText()));
        } catch (JsonProcessingException e) {
            System.out.printf("Ошибка при получении списка метрик: %s%n",e.getMessage());
        }
    }

    @Override
    public void showMetric(String metricName) {
        try {
            String url = ACTUATOR_BASE_URL + "/metrics/" + metricName;
            ResponseEntity<String> response;
            try {
                response = restTemplate.getForEntity(url, String.class);
            } catch (HttpClientErrorException e) {
                System.out.printf("Ошибка при получении метрики: %s%n", metricName);
                return;
            }
            JsonNode metricData = objectMapper.readTree(response.getBody());
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(metricData);
            System.out.printf("Метрика '%s': ", metricName);
            System.out.println(prettyJson);
        } catch (JsonProcessingException e) {
            System.out.printf("Ошибка при получении метрики '%s': %s%n", metricName, e.getMessage());
            System.out.println("Используйте команду 'metrics' для просмотра списка доступных метрик");
        }
    }
}