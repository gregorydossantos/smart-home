package com.fiap.gregory.smarthome.domain.useful;

import com.fiap.gregory.smarthome.domain.services.exceptions.BadRequestViolationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.BAD_REQUEST;

@Component
@AllArgsConstructor
public class ValidationUseful {

    private final Validator validator;

    public <T> void validateRequest(T request) {
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new BadRequestViolationException(BAD_REQUEST);
        }
    }
}
