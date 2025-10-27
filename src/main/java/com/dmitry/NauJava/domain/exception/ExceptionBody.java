package com.dmitry.NauJava.domain.exception;

/**
 * Тело ошибки.
 * Содержит описание ошибки.
 */
public class ExceptionBody {

    private String message;

    public ExceptionBody(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}