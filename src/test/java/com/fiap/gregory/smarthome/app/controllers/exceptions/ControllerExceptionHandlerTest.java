package com.fiap.gregory.smarthome.app.controllers.exceptions;

import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ControllerExceptionHandlerTest {

    private static final String ADDRESS_ALREADY_EXISTS = "Endereço já cadastrado!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";
    private static final Integer ERROR_HTTP_CODE = 400;
    public static final String PATH_ADDRESS_REGISTER = "/address-register";
    public static final LocalDateTime DATE_TIME_ERROR = LocalDateTime.now();

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Shloud be return DataIntegratyViolationException")
    void testWhenAddressAlreadyExists() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegratyViolationException(
                new DataIntegratyViolationException(ADDRESS_ALREADY_EXISTS),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ADDRESS_ALREADY_EXISTS, response.getBody().getError());
        assertEquals(ERROR_HTTP_CODE, response.getBody().getStatus());
        assertNotEquals(PATH_ADDRESS_REGISTER, response.getBody().getPath());
        assertNotEquals(DATE_TIME_ERROR, response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Should be return BadRequestViolationException")
    void testWhenRequestComesWithError() {
        ResponseEntity<StandardError> response = exceptionHandler.badRequestViolationException(
                new BadRequestViolationException(BAD_REQUEST),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(BAD_REQUEST, response.getBody().getError());
        assertEquals(ERROR_HTTP_CODE, response.getBody().getStatus());
        assertNotEquals(PATH_ADDRESS_REGISTER, response.getBody().getPath());
        assertNotEquals(DATE_TIME_ERROR, response.getBody().getTimestamp());
    }
}