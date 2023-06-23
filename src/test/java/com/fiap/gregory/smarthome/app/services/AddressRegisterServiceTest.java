package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.AddressRegister;
import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.repositories.AddressRegisterRepository;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressRegisterServiceTest {

    private static final Long ID = 1L;
    private static final String STREET = "Teste";
    private static final Integer NUMBER = 123;
    private static final String DISTRICT = "Teste";
    private static final String CITY = "Testelandia";
    private static final String STATE = "SP";

    private AddressRegisterRequest request;
    private AddressRegisterDto dto;

    private AddressRegister addressRegister;

    @InjectMocks
    private AddressRegisterService service;

    @Mock
    AddressRegisterRepository repository;

    @Mock
    ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() {
        request = AddressRegisterRequest.builder()
                .street(STREET)
                .number("123")
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .build();

        dto = AddressRegisterDto.builder()
                .id(ID)
                .street(STREET)
                .number(NUMBER)
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .build();

        addressRegister = AddressRegister.builder()
                .id(ID)
                .street(STREET)
                .number(NUMBER)
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .build();
    }

    @Test
    @DisplayName("Should be return success")
    void testCreateAddressSuccess() {
        when(repository.save(any())).thenReturn(addressRegister);
        when(mapper.map(any(), any())).thenReturn(dto);

        AddressRegisterDto response = service.create(request);

        assertNotNull(response);
        assertEquals(AddressRegisterDto.class, response.getClass());
        assertEquals(addressRegister.getId(), response.getId());
        assertEquals(addressRegister.getStreet(), response.getStreet());
        assertEquals(addressRegister.getNumber(), response.getNumber());
        assertEquals(addressRegister.getDistrict(), response.getDistrict());
        assertEquals(addressRegister.getCity(), response.getCity());
        assertEquals(addressRegister.getState(), response.getState());
    }

    @Test
    @DisplayName("Should be return BadRequestViolationException")
    void testRequestNullOrEmpty() {
        when(repository.save(any())).thenReturn(addressRegister);

        assertThrows(BadRequestViolationException.class, () -> {
            service.create(null);
        });
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testAddressAlreadyExists() {
        when(repository.findByStreetAndNumber(anyString(), anyInt())).thenReturn(addressRegister);

        assertThrows(DataIntegratyViolationException.class, () -> {
            service.create(request);
        });
    }
}