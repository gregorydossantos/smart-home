package com.fiap.gregory.smarthome.app.services.exceptions;

public class BadRequestViolationException extends RuntimeException {

    public BadRequestViolationException(String message) {
        super(message);
    }
}
