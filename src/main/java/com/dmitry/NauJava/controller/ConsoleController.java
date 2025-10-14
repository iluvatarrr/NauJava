package com.dmitry.NauJava.controller;
/**
 Интерфейс для контроллера,
 может быть использован для моков,
 добавляет слабую связность по канонам инверссии зависимостей
**/
public interface ConsoleController {
    void processCommand(String[] cmd);
}