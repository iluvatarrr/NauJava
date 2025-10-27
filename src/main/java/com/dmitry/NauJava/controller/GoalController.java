package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.dto.GoalDto;
import com.dmitry.NauJava.mapper.GoalMapper;
import com.dmitry.NauJava.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Контроллер целей.
 * Работает напрямую с репозиторием по условию задания.
 * Позволяет найти отсортированные цели по названию, также по названию и описанию
 */
@RestController
@RequestMapping("/goals")
public class GoalController {
    private final GoalRepository goalRepository;
    private final GoalMapper mapper;

    @Autowired
    public GoalController(GoalRepository goalRepository, GoalMapper mapper) {
        this.goalRepository = goalRepository;
        this.mapper = mapper;
    }

    @GetMapping("/find/ordered")
    public List<GoalDto> findByTitleOrderByCreatedAt(@RequestParam String title) {
        return mapper.toDto(goalRepository.findByTitleOrderByCreatedAt(title));
    }

    @GetMapping("/find")
    public List<GoalDto> findByTitleAndDescription(@RequestParam String title, @RequestParam String description) {
        return mapper.toDto(goalRepository.findByTitleAndDescription(title, description));
    }
}