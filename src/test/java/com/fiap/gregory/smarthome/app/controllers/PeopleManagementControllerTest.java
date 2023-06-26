package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.PeopleManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PeopleManagementControllerTest {

    private final static String NAME = "Teste";
    private final static String BIRHDAY = "1989-12-28";
    private final static String GENDER = "M";
    private final static String PARENTAGE = "Father";
    private final static String ATIVO = "S";

    private PeopleManagementRequest request;
    private PeopleManagementDto peopleManagementDto;

    @InjectMocks
    private PeopleManagementController controller;

    @Mock
    private PeopleManagementService service;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() throws ParseException {
        peopleManagementDto = PeopleManagementDto.builder()
                .id(1L)
                .name(NAME)
                .birthday(convertToDate(BIRHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .ativo(ATIVO)
                .build();

        request = PeopleManagementRequest.builder()
                .name(NAME)
                .birthday(BIRHDAY)
                .gender(GENDER)
                .parentage(PARENTAGE)
                .ativo(ATIVO)
                .build();
    }

    @Test
    @DisplayName("Should be return a Http status 201 - Created")
    void createAddressWithSuccess() {
        when(service.create(any())).thenReturn(peopleManagementDto);

        ResponseEntity<PeopleManagementDto> response = controller.create(request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}