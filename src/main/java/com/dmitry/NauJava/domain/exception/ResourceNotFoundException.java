package com.dmitry.NauJava.domain.exception;
/**
Универсальная ошибка для ненайденных данных
 **/
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}