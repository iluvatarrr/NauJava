package com.dmitry.NauJava.controller.impl;

import com.dmitry.NauJava.controller.ConsoleController;
import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.service.ActuatorService;
import com.dmitry.NauJava.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
* Контроллер работающий с слоями сервисов обрабатывает команды,
* кетчит ошибки, не роняя приложение, это юзерфрендли
*/
@Controller
public class ConsoleControllerImpl implements ConsoleController {
    private final GoalService goalService;
    private final ActuatorService actuatorService;
    @Autowired
    public ConsoleControllerImpl(GoalService goalService, ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
        this.goalService = goalService;
    }

    @Override
    public void processCommand(String[] cmd)
    {
        switch (cmd[0].toLowerCase())
        {
            case "create" ->
            {
                goalService.save(Long.valueOf(cmd[1]), cmd[2]);
                System.out.println("Цель успешно добавлена...");
            }
            case "delete" ->
            {
                try {
                    goalService.deleteById(Long.valueOf(cmd[1]));
                    System.out.println("Цель успешно удалена...");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Цель не найдена");
                }
            }
            case "update" ->
            {
                try {
                    goalService.updateGoalTitle(Long.valueOf(cmd[1]), cmd[2]);
                    System.out.println("Цель успешно обновлена...");
                } catch (ResourceNotFoundException e) {
                    System.out.println("Цель не найдена");
                }
            }
            case "find" ->
            {
                try {
                    var goal = goalService.findById(Long.valueOf(cmd[1]));
                    System.out.println("Цель успешно найдена...");
                    System.out.println(goal);
                } catch (ResourceNotFoundException e) {
                    System.out.println("Цель не найдена");
                }
            }
            case "all" ->
            {
                System.out.println("Цели: ");
                var goals = goalService.findAll();
                if (goals.isEmpty()) {
                    System.out.println("Целей пока нет");
                } else {
                    goals.forEach(System.out::println);
                }
            }
            case "metrics" -> {
                if (cmd.length == 1) {
                    actuatorService.showAvailableMetrics();
                } else {
                    actuatorService.showMetric(cmd[1]);
                }
            }
            default -> System.out.println("Введена неизвестная команда...");
        }
    }
}