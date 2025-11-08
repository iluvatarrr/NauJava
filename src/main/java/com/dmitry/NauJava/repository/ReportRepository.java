package com.dmitry.NauJava.repository;

import com.dmitry.NauJava.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Слой для взаимодействия с данными
 * Реализует JPA репозиторий.
 */
@RepositoryRestResource(path = "report")
public interface ReportRepository extends JpaRepository<Report, Long> {
}