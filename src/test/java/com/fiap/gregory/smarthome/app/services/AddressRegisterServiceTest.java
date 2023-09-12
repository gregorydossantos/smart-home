package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.AddressRegister;
import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.AddressRegisterRepository;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
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

    private PeopleManagement people;

    @InjectMocks
    private AddressRegisterService service;

    @Mock
    private AddressRegisterRepository repository;

    @Mock
    private PeopleManagementRepository peopleRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private ValidationUseful validator;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() throws ParseException {
        request = AddressRegisterRequest.builder()
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

        addressRegister = AddressRegister.builder()
                .id(ID)
                .street(STREET)
                .number(NUMBER)
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .peopleManagement(PeopleManagement.builder()
                        .id(1L)
                        .name("Teste")
                        .birthday(convertToDate("01-01-2023"))
                        .gender("M")
                        .parentage("Father")
                        .build())
                .build();

        people = PeopleManagement.builder()
                .id(1L)
                .name("Teste")
                .birthday(convertToDate("01-01-2023"))
                .gender("M")
                .parentage("Father")
                .build();
    }

    @Test
    @DisplayName("[CREATE] Should be return success after create an address")
    void testCreateAddress() {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
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
    @DisplayName("[READ] Should be return an address list")
    void testReadAddress() {
        when(repository.findAll()).thenReturn(List.of(addressRegister));
        when(mapper.map(any(), any())).thenReturn(dto);

        List<AddressRegisterDto> response = service.read();
        assertNotNull(response);
    }

    @Test
    @DisplayName("[UPDATE] Should be return a update address")
    void testUpdateAddress() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(addressRegister));
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(mapper.map(any(), any())).thenReturn(dto);

        var request = AddressRegisterRequest.builder()
                .street("street")
                .number("987")
                .district("district")
                .city("city")
                .state("RJ")
                .peopleId("1")
                .build();

        AddressRegisterDto response = service.update(1L, request);
        assertNotNull(response);
        assertNotEquals(response.getStreet(), request.getStreet());
        assertNotEquals(response.getNumber(), Integer.parseInt(request.getNumber()));
        assertNotEquals(response.getDistrict(), request.getDistrict());
        assertNotEquals(response.getState(), request.getState());
        assertNotEquals(response.getCity(), request.getCity());
    }

    @Test
    @DisplayName("[DELETE] Should be return success after delete an address")
    void testDeleteAddress() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(addressRegister));
        assertDoesNotThrow(() -> service.delete(ID));
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException when address already exists")
    void testAddressAlreadyExists() {
        when(repository.findByStreetAndDistrictAndCity(anyString(), anyString(), anyString()))
                .thenReturn(addressRegister);
        assertThrows(DataIntegratyViolationException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when people not exists")
    void testPeopleNotExists() {
        assertThrows(DataEmptyOrNullException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call read method")
    void testReadAddressNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.read());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call update method")
    void testUpdateAddressNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.update(ID, request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call delete method")
    void testDeleteAddressNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.delete(ID));
    }
}