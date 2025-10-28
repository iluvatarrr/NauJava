package com.dmitry.NauJava.controller;

import com.dmitry.NauJava.domain.exception.ExceptionBody;
import com.dmitry.NauJava.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Класс обработчик ошибок.
 * Позволяет обрабатывать и выводить ошибки.
 * Обрабатывает: ResourceNotFoundException
 */
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage());
    }
}