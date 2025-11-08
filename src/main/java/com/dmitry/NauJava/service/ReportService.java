package com.dmitry.NauJava.service;

import com.dmitry.NauJava.domain.report.Report;

/**
 * Интерфейс для взаимодействия с отчетами
 */
public interface ReportService {
    void startReportFormation(Long reportId);
    void formationReportAsync(Report report);
    Long create(String content);
    String getContent(Long id);
    Report findById(Long id);
}
