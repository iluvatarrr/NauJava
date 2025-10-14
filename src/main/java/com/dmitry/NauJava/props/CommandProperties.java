package com.dmitry.NauJava.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * Пропсы описывающие команды, помогает избавиться от хардкода в приложении,
 * позволяет более гибко работать с командами
 * Содержит список сложного объекта, манипулирует им
 * private String pattern; - как вводить
 * private String description; - что за команда
 * private Integer countOfArguments; - сколько аргументов в команде
 * private String typeOfCommand; - тип команды
 */
@Component
@ConfigurationProperties(prefix = "app.commands")
public class CommandProperties {
    private Map<String, CommandConfig> commandList;

    public void printCommands() {
        if (commandList != null && !commandList.isEmpty()) {
            System.out.println("Commands:\n");
            commandList.values().forEach(System.out::println);
        } else {
            System.out.println("No commands configured");
        }
    }

    public Map<String, CommandConfig> getCommandList() {
        return commandList;
    }

    public void setCommandList(Map<String, CommandConfig> commandList) {
        this.commandList = commandList;
    }

    public static class CommandConfig {
        private String pattern;
        private String description;
        private Integer countOfArguments;
        private String typeOfCommand;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getCountOfArguments() {
            return countOfArguments;
        }

        public void setCountOfArguments(Integer countOfArguments) {
            this.countOfArguments = countOfArguments;
        }

        public String getTypeOfCommand() {
            return typeOfCommand;
        }

        public void setTypeOfCommand(String typeOfCommand) {
            this.typeOfCommand = typeOfCommand;
        }

        @Override
        public String toString() {
            return String.format("%s - %s", pattern, description);
        }
    }
}