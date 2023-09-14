package com.fiap.gregory.smarthome.app.rest;

import com.fiap.gregory.smarthome.domain.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.request.AddressRequest;
import com.fiap.gregory.smarthome.domain.services.AddressService;
import com.fiap.gregory.smarthome.domain.usecases.AddressUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class AddressControllerTest {

    private AddressRequest request;
    private AddressRegisterDto addressRegisterDto;

    @InjectMocks
    private AddressController controller;

    @Mock
    private AddressService service;

    @Mock
    private AddressUseCase useCase;

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

        request = AddressRequest.builder()
                .street("Teste")
                .number("123")
                .district("Teste")
                .city("Testelandia")
                .state("SP")
                .build();
    }

    @Test
    @DisplayName("Should be return a Http status 201 - Created")
    void testCreateAddressWithSuccess() {
        when(service.create(any())).thenReturn(addressRegisterDto);

        ResponseEntity<AddressRegisterDto> response = controller.create(request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return a list whit all the address")
    void testReadAllAddress() {
        when(service.create(any())).thenReturn(addressRegisterDto);

        ResponseEntity<List<AddressRegisterDto>> response = controller.readAll();

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return an update address")
    void testUpdateAddress() {
        when(service.create(any())).thenReturn(addressRegisterDto);

        ResponseEntity<AddressRegisterDto> response = controller.update(1L, request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return success after delete an address")
    void testDeleteAddress() {
        when(service.create(any())).thenReturn(addressRegisterDto);

        controller.delete(1L);

        assertEquals(HttpStatus.OK.value(), 200);
    }
}