package com.dmitry.NauJava.configuration;

import com.dmitry.NauJava.controller.ConsoleController;
import com.dmitry.NauJava.controller.impl.ConsoleControllerImpl;
import com.dmitry.NauJava.props.CommandProperties;
import com.dmitry.NauJava.service.ConsoleValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Scanner;

/**
 * Общий класс конфигурации консоли.
 * В нем регистрируется бин, необходимые для работы с консолью,
 * Если ввод пустой, логика просит вывести еще раз команду
 * CommandProperties отвечает для команды, в нем есть данные о команде,
 * ее описании, типе и количестве аргументов
 * Валидатор выступает в качестве визитора, который инкапсулирует валидацию в себе
 * После прохождения валидации , контроллер выполняет метод вызова к слою сервисов
 */
@Configuration
public class ConsoleConfig {
    private final CommandProperties commandProperties;
    private final ConsoleController consoleController;
    private final ConsoleValidatorService consoleValidatorService;

    @Autowired
    public ConsoleConfig(CommandProperties commandProperties,
                         ConsoleControllerImpl consoleController,
                         ConsoleValidatorService consoleValidatorService) {
        this.commandProperties = commandProperties;
        this.consoleController = consoleController;
        this.consoleValidatorService = consoleValidatorService;
    }

    @Bean
    public CommandLineRunner commandScanner()
    {
        return args ->
        {
            try (Scanner scanner = new Scanner(System.in))
            {
                commandProperties.printCommands();
                while (true)
                {
                    System.out.print("> ");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.isEmpty()) {
                        System.out.println("Введите команду");
                        continue;
                    }
                    String[] cmd = input.split(" ");
                    switch (cmd[0].toLowerCase()) {
                        case "help" -> commandProperties.printCommands();
                        case "exit" -> {
                            System.out.println("Выход из программы...");
                            System.exit(0);
                        }
                        default -> {
                            if (consoleValidatorService.isNonValidConsole(cmd)) {
                                System.out.println("Введите команду повторно или введите exit для выхода");
                                continue;
                            }
                        }
                    }
                    consoleController.processCommand(cmd);
                }
            }
        };
    }
}