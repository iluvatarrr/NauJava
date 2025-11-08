package com.dmitry.NauJava.domain.report;

import jakarta.persistence.*;

/**
 * Репорт - сущность для взаимодействия с отчетом.
 */
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @Column(columnDefinition = "TEXT")
    private String content;

    public Report() {}

    public Report(ReportStatus status, String content) {
        this.status = status;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
