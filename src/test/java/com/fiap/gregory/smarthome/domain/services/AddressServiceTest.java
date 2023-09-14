package com.fiap.gregory.smarthome.domain.services;

import com.fiap.gregory.smarthome.app.request.AddressRequest;
import com.fiap.gregory.smarthome.domain.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.domain.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.domain.usecases.AddressUseCase;
import com.fiap.gregory.smarthome.infra.db.models.Address;
import com.fiap.gregory.smarthome.infra.db.models.People;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;

import static com.fiap.gregory.smarthome.domain.useful.StringUseful.convertToDate;

@SpringBootTest
@ActiveProfiles("test")
class AddressServiceTest {
    private static final Long ID = 1L;
    private static final String STREET = "Teste";
    private static final Integer NUMBER = 123;
    private static final String DISTRICT = "Teste";
    private static final String CITY = "Testelandia";
    private static final String STATE = "SP";

    private AddressRequest request;

    private AddressRegisterDto dto;

    private Address address;

    private People people;

    @InjectMocks
    private AddressService service;

    @Mock
    private AddressUseCase useCase;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() throws ParseException {
        request = AddressRequest.builder()
                .street(STREET)
                .number("123")
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .peopleId("1")
                .build();

        dto = AddressRegisterDto.builder()
                .id(ID)
                .street(STREET)
                .number(NUMBER)
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .peopleManagement(PeopleManagementDto.builder()
                        .id(1L)
                        .name("Teste")
                        .birthday(convertToDate("01-01-2023"))
                        .gender("M")
                        .parentage("Father")
                        .build())
                .build();

        address = Address.builder()
                .id(ID)
                .street(STREET)
                .number(NUMBER)
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .people(People.builder()
                        .id(1L)
                        .name("Teste")
                        .birthday(convertToDate("01-01-2023"))
                        .gender("M")
                        .parentage("Father")
                        .build())
                .build();

        people = People.builder()
                .id(1L)
                .name("Teste")
                .birthday(convertToDate("01-01-2023"))
                .gender("M")
                .parentage("Father")
                .build();
    }

    @Test
    @DisplayName("[CREATE] Should be created an address")
    void testCreate() {}

    @Test
    @DisplayName("[READ] Should be return a list of the address")
    void testRead() {}

    @Test
    @DisplayName("[UPDADE] Should be update an address")
    void testUpdate() {}

    @Test
    @DisplayName("[DELETE] Should be deleted an address")
    void testDelete() {}
}