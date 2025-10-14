package com.dmitry.NauJava.service;

/**
 * Интерфес содержащий два метода: для вывода всех названий метрик, а так же для вывода спаршенного json метрики
 */
public interface ActuatorService {
    void showAvailableMetrics();
    void showMetric(String metricName);
}
