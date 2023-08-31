package com.fiap.gregory.smarthome.app.controllers.exceptions;

import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadRequestViolationException.class)
    public ResponseEntity<StandardError> badRequestViolationException(BadRequestViolationException ex,
                                                                      HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegratyViolationException.class)
    public ResponseEntity<StandardError> dataIntegratyViolationException(DataIntegratyViolationException ex,
                                                                         HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataEmptyOrNullException.class)
    public ResponseEntity<StandardError> dataEmptyOrNullException(DataEmptyOrNullException ex,
                                                                  HttpServletRequest request) {
        StandardError error = new StandardError(
                LocalDateTime.now(), BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI()
        );
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }
}
