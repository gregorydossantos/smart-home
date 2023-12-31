package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.AddressRegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressRegisterControllerTest {

    private AddressRegisterRequest request;
    private AddressRegisterDto addressRegisterDto;

    @InjectMocks
    private AddressRegisterController controller;

    @Mock
    private AddressRegisterService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() {
        addressRegisterDto = AddressRegisterDto.builder()
                .id(1L)
                .street("Teste")
                .number(123)
                .district("Teste")
                .city("Testelandia")
                .state("SP")
                .build();

        request = AddressRegisterRequest.builder()
                .street("Teste")
                .number("123")
                .district("Teste")
                .city("Testelandia")
                .state("SP")
                .build();
    }

    @Test
    @DisplayName("Should be return a Http status 201 - Created")
    void createAddressWithSuccess() {
        when(service.create(any())).thenReturn(addressRegisterDto);

        ResponseEntity<AddressRegisterDto> response = controller.create(request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}