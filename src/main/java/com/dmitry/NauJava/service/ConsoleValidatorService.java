package com.dmitry.NauJava.service;
/**
 Функциональный нтерфейс для контракта валидации
 Содержит ровно 1 метод, чтобы через него формировать вердикт
 **/
@FunctionalInterface
public interface ConsoleValidatorService {
    boolean isNonValidConsole(String[] cmd);
}