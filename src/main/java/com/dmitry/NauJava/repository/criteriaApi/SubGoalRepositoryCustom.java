package com.dmitry.NauJava.repository.criteriaApi;

import com.dmitry.NauJava.domain.subGoal.SubGoal;
import java.util.List;

/**
 * SubGoalRepositoryCustom - интерфейс кастомного репозитория для подцелей
 * Прописан контракт, который позволяет находить подцель по названию и описанию, а также
 * нахожить подцель по полю названия связной сущности (цели)
 */
public interface SubGoalRepositoryCustom {
    List<SubGoal> findByTitleAndDescription(String title, String description);
    List<SubGoal> findByGoalTitle(String goalTitle);
}