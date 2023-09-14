package com.fiap.gregory.smarthome.domain.services.exceptions;

public class BadRequestViolationException extends RuntimeException {

    public BadRequestViolationException(String message) {
        super(message);
    }
}
