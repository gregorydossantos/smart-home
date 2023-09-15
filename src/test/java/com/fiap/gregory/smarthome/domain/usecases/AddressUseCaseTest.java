package com.fiap.gregory.smarthome.domain.usecases;

import com.fiap.gregory.smarthome.app.request.AddressRequest;
import com.fiap.gregory.smarthome.domain.dtos.AddressDto;
import com.fiap.gregory.smarthome.domain.dtos.PeopleDto;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.domain.useful.ValidationUseful;
import com.fiap.gregory.smarthome.infra.db.models.Address;
import com.fiap.gregory.smarthome.infra.db.models.People;
import com.fiap.gregory.smarthome.infra.db.repositories.AddressRepository;
import com.fiap.gregory.smarthome.infra.db.repositories.PeopleRepository;
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

import static com.fiap.gregory.smarthome.domain.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class AddressUseCaseTest {
    private static final Long ID = 1L;
    private static final String STREET = "Teste";
    private static final Integer NUMBER = 123;
    private static final String DISTRICT = "Teste";
    private static final String CITY = "Testelandia";
    private static final String STATE = "SP";

    private AddressRequest request;

    private AddressDto dto;

    private Address address;

    private People people;

    @InjectMocks
    private AddressUseCase useCase;

    @Mock
    private AddressRepository repository;

    @Mock
    private PeopleRepository peopleRepository;

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
        request = AddressRequest.builder()
                .street(STREET)
                .number("123")
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .peopleId("1")
                .build();

        dto = AddressDto.builder()
                .id(ID)
                .street(STREET)
                .number(NUMBER)
                .district(DISTRICT)
                .city(CITY)
                .state(STATE)
                .peopleManagement(PeopleDto.builder()
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
    @DisplayName("[CREATE] Should be return success after create an address")
    void testCreateAddress() {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(repository.save(any())).thenReturn(address);
        when(mapper.map(any(), any())).thenReturn(dto);

        AddressDto response = useCase.create(request);

        assertNotNull(response);
        assertEquals(AddressDto.class, response.getClass());
        assertEquals(address.getId(), response.getId());
        assertEquals(address.getStreet(), response.getStreet());
        assertEquals(address.getNumber(), response.getNumber());
        assertEquals(address.getDistrict(), response.getDistrict());
        assertEquals(address.getCity(), response.getCity());
        assertEquals(address.getState(), response.getState());
    }

    @Test
    @DisplayName("[READ] Should be return an address list")
    void testReadAddress() {
        when(repository.findAll()).thenReturn(List.of(address));
        when(mapper.map(any(), any())).thenReturn(dto);

        List<AddressDto> response = useCase.read();
        assertNotNull(response);
    }

    @Test
    @DisplayName("[UPDATE] Should be return a update address")
    void testUpdateAddress() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(mapper.map(any(), any())).thenReturn(dto);

        var request = AddressRequest.builder()
                .street("street")
                .number("987")
                .district("district")
                .city("city")
                .state("RJ")
                .peopleId("1")
                .build();

        AddressDto response = useCase.update(1L, request);
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
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));
        assertDoesNotThrow(() -> useCase.delete(ID));
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException when address already exists")
    void testAddressAlreadyExists() {
        when(repository.findByStreetAndDistrictAndCity(anyString(), anyString(), anyString()))
                .thenReturn(address);
        assertThrows(DataIntegratyViolationException.class, () -> useCase.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when people not exists")
    void testPeopleNotExists() {
        assertThrows(DataEmptyOrNullException.class, () -> useCase.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call read method")
    void testReadAddressNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.read());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call update method")
    void testUpdateAddressNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.update(ID, request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call delete method")
    void testDeleteAddressNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.delete(ID));
    }
}