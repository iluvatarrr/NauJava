package com.dmitry.NauJava.mapper;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.dto.GoalDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация маппера для целей
 */
@Service
public class GoalMapper implements Mappable<Goal, GoalDto> {
    @Override
    public GoalDto toDto(Goal entity) {
        if (entity == null) {
            return null;
        }

        return new GoalDto(
                entity.getTitle(),
                entity.getDescription(),
                entity.getGoalStatus(),
                entity.getGoalCategory(),
                entity.getCreatedAt()
        );
    }

    @Override
    public Goal toEntity(GoalDto dto) {
        if (dto == null) {
            return null;
        }

        Goal goal = new Goal();
        goal.setTitle(dto.title());
        goal.setDescription(dto.description());
        goal.setGoalStatus(dto.goalStatus());
        goal.setGoalCategory(dto.goalCategory());
        goal.setCreatedAt(dto.createdAt());
        return goal;
    }

    @Override
    public List<GoalDto> toDto(List<Goal> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Goal> toEntity(List<GoalDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}