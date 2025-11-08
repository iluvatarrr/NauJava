package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ReportController - контроллер для работы с отчетами.
 * Позволяет создавать отчет, получать контент отчета.
 */
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public Long createReport(String content) {
        var reportId = reportService.create(content);
        reportService.startReportFormation(reportId);
        return reportId;
    }

    @GetMapping("/{id}")
    public String getByReportId(@PathVariable Long id) {
        return reportService.getContent(id);
    }
}
