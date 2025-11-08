package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.report.Report;
import com.dmitry.NauJava.domain.report.ReportStatus;
import com.dmitry.NauJava.repository.ReportRepository;
import com.dmitry.NauJava.service.GoalService;
import com.dmitry.NauJava.service.ReportService;
import com.dmitry.NauJava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * ReportServiceImpl - реализация ReportService.
 * Способен выдавать контент отчета, создавать и формировать отчет.
 */
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final GoalService goalService;
    private final UserService userService;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, GoalService goalService, UserService userService) {
        this.reportRepository = reportRepository;
        this.goalService = goalService;
        this.userService = userService;
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                String.format("Report with id: %s, not found", id)));
    }

    @Override
    public String getContent(Long id) {
        var report = findById(id);
        if (!report.getStatus().equals(ReportStatus.FINISHED)) {
            return String.format("Report with id: %s, not finished, status: %s", id, report.getStatus());
        }
        return report.getContent();
    }

    @Override
    public Long create(String content) {
        var report = new Report();
        report.setContent(content);
        report.setStatus(ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public void formationReportAsync(Report report) {
        CompletableFuture.runAsync(() -> {
            try {
                long startTime = System.currentTimeMillis();

                long userCountStartTime = System.currentTimeMillis();
                CompletableFuture<Long> userCountFuture = CompletableFuture
                        .supplyAsync(this::calculateUserCountInThread);
                Long userCount = userCountFuture.join();
                long userCountElapsed = System.currentTimeMillis() - userCountStartTime;

                long goalsStartTime = System.currentTimeMillis();
                CompletableFuture<List<Goal>> goalListFuture = CompletableFuture
                        .supplyAsync(this::getAllGoal);
                List<Goal> goalList = goalListFuture.join();
                long goalsElapsed = System.currentTimeMillis() - goalsStartTime;

                long totalElapsed = System.currentTimeMillis() - startTime;

                String reportContent = generateReport(userCount, userCountElapsed, goalList,
                        goalsElapsed, totalElapsed, report.getContent());
                report.setContent(reportContent);
                report.setStatus(ReportStatus.FINISHED);
                reportRepository.save(report);
            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                report.setContent(String.format("Error during report generation: %s", e.getMessage()));
                reportRepository.save(report);
            }
        });
    }

    private String generateReport(Long userCount, long userCountTime,
                                  List<Goal> goals, long goalsTime,
                                  long totalTime, String originalContent) {
        var goalsString =  String.join("<br>", goals.stream().map(Goal::toString).toList());
        return String.format(
                """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Report</title>
                </head>
                <body>
                    <h1>Application Report</h1>
                    <div>Users: %d, time: %d ms</div>
                    <div>Goals: %d, time: %d ms</div>
                    <div>Total time: %d ms</div>
                    <h2>Goals List:</h2>
                    <p>%s</p>
                    <strong>Original Content:</strong><br>%s
                </body>
                </html>
                """,
                userCount, userCountTime,
                goals.size(), goalsTime,
                totalTime,
                goalsString,
                originalContent
        );
    }

    private Long calculateUserCountInThread() {
        return userService.findAll().stream().count();
    }

    private List<Goal> getAllGoal() {
        return goalService.findAll();
    }

    public void startReportFormation(Long reportId) {
        Report report = findById(reportId);
        formationReportAsync(report);
    }
}
