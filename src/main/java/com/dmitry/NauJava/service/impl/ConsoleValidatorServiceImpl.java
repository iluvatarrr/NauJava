package com.dmitry.NauJava.service.impl;

import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import com.dmitry.NauJava.props.CommandProperties;
import com.dmitry.NauJava.service.ConsoleValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* Класс для валидирования введенных данных (по концепции напоминает паттерн визитор) проверяет верно ли введена команда
* Оперирует классом CommandProperties для гибкой работы с параметрами команды
*/
@Service
public class ConsoleValidatorServiceImpl implements ConsoleValidatorService {
    @Autowired
    private CommandProperties commandProperties;

    @Override
    public boolean isNonValidConsole(String[] cmd) {
        var command = cmd[0];
        if (commandProperties.getCommandList().containsKey(command)) {
            CommandProperties.CommandConfig propsCommand;
            try {
                propsCommand = commandProperties.getCommandList().values()
                        .stream()
                        .filter(e -> command.equals(e.getPattern().split(" ")[0])
                                && e.getCountOfArguments() == cmd.length).findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Invalid command"));
            } catch (ResourceNotFoundException e) {
                System.out.println("Такой команды нет");
                return true;
            }
            return validateConsoleCommand(cmd, propsCommand);
        }
        return true;
    }

    private boolean validateConsoleCommand(String[] cmd, CommandProperties.CommandConfig propsCommand) {
        var countOfArguments = propsCommand.getCountOfArguments();
        if (cmd.length != countOfArguments) {
            System.out.printf("Неверный ввод %s, должено быть количество аргументов равное %s%n",
                    cmd[0], countOfArguments);
            return true;
        }
        if (propsCommand.getTypeOfCommand().equals("performing")) {
            if (countOfArguments > 1) {
                try {
                    Integer.parseInt(cmd[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Второй аргумент должен быть числом!");
                    return true;
                }
            }
            if (countOfArguments > 2) {
                return cmd[2].isEmpty();
            }
        } else {
            if (countOfArguments > 1) {
                return cmd[1].isEmpty();
            }
        }
        return false;
    }
}