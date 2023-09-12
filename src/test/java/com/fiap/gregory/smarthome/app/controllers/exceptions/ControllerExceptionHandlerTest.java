package com.fiap.gregory.smarthome.app.controllers.exceptions;

import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
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
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ControllerExceptionHandlerTest {

    private static final String DATA_NOT_FOUND = "Registro não encontrada!";
    private static final String DATA_ALREADY_EXISTS = "Registro já cadastrado!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";
    private static final Integer ERROR_HTTP_CODE = 400;
    public static final String PATH_ADDRESS_REGISTER = "/address-register";
    public static final String PATH_HOME_APPLIANCE = "/home-appliance";
    public static final String PATH_PEOPLE_MANAGER = "/people";
    public static final LocalDateTime DATE_TIME_ERROR = LocalDateTime.now();

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testWhenHomeApplianceAlreadyExists() {
        ResponseEntity<StandardError> response = exceptionHandler.dataIntegratyViolationException(
                new DataIntegratyViolationException(DATA_ALREADY_EXISTS),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(DATA_ALREADY_EXISTS, response.getBody().getError());
        assertEquals(ERROR_HTTP_CODE, response.getBody().getStatus());
        assertNotEquals(PATH_HOME_APPLIANCE, response.getBody().getPath());
        assertNotEquals(DATE_TIME_ERROR, response.getBody().getTimestamp());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException")
    void testWhenDataNotFound() {
        ResponseEntity<StandardError> response = exceptionHandler.dataEmptyOrNullException(
                new DataEmptyOrNullException(DATA_NOT_FOUND),
                new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(DATA_NOT_FOUND, response.getBody().getError());
        assertEquals(ERROR_HTTP_CODE, response.getBody().getStatus());
        assertNotEquals(PATH_PEOPLE_MANAGER, response.getBody().getPath());
        assertNotEquals(DATE_TIME_ERROR, response.getBody().getTimestamp());
    }
}