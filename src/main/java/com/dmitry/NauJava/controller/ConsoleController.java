package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ConsoleController {
    private final GoalService goalService;

    @Autowired
    public ConsoleController(GoalService goalService) {
        this.goalService = goalService;
    }

    public void processCommand(String input)
    {
        String[] cmd = input.split(" ");
        switch (cmd[0])
        {
            case "create" ->
            {
                goalService.create(Long.valueOf(cmd[1]), cmd[2]);
                System.out.println("Цель успешно добавленв...");
            }
            case "delete" ->
            {
                goalService.deleteById(Long.valueOf(cmd[1]));
                System.out.println("Цель успешно удаленв...");
            }
            case "update" ->
            {
                goalService.updateGoalTitle(Long.valueOf(cmd[1]), cmd[2]);
                System.out.println("Цель успешно обновлена...");
            }
            case "find" ->
            {
                var goal = goalService.findById(Long.valueOf(cmd[1]));
                System.out.println("Цель успешно найдена...");
                System.out.println(goal);
            }
            default -> System.out.println("Введена неизвестная команда...");
        }
    }
}