package com.fiap.gregory.smarthome.app.exceptions;

public class BadRequestViolationException extends RuntimeException {

    public BadRequestViolationException(String message) {
        super(message);
    }
}
