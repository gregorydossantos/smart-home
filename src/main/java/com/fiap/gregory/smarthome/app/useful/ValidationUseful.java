package com.fiap.gregory.smarthome.app.useful;

import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUseful {

    private static final String BAD_REQUEST = "Erro no contexto da requisição!";

    @Autowired
    private Validator validator;

    public <T> void validateRequest(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new BadRequestViolationException(BAD_REQUEST);
        }
    }
}
