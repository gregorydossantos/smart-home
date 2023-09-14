package com.fiap.gregory.smarthome.domain.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = IntegerValueValidator.class)
public @interface ValidateInteger {
    String message();

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
