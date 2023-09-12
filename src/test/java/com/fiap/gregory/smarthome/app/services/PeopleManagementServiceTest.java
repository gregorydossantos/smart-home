package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import org.junit.jupiter.api.*;
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
class PeopleManagementServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String BIRTHDAY = "01-01-2023";
    private static final String GENDER = "M";
    private static final String PARENTAGE = "Father";
    private PeopleManagementRequest request;
    private PeopleManagementDto dto;
    private PeopleManagement peopleManagement;
    @InjectMocks
    private PeopleManagementService service;
    @Mock
    private PeopleManagementRepository repository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private ValidationUseful validator;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    @AfterEach
    void clear() {
        repository.deleteAll();
    }

    private void mockDatas() throws ParseException {
        request = PeopleManagementRequest.builder()
                .name(NAME)
                .birthday(BIRTHDAY)
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        dto = PeopleManagementDto.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        peopleManagement = PeopleManagement.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        repository.saveAndFlush(peopleManagement);
    }

    @Test
    @DisplayName("[CREATE] Should be return success after create a people")
    void testCreatePeopleSuccess() throws ParseException {
        when(repository.save(any())).thenReturn(peopleManagement);
        when(mapper.map(any(), any())).thenReturn(dto);

        PeopleManagementDto response = service.create(request);

        assertNotNull(response);
        assertEquals(PeopleManagementDto.class, response.getClass());
        assertEquals(request.getName(), response.getName());
        assertEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("[READ] Should be return a list with all the people")
    void testReadPeople() throws ParseException {
        when(repository.findAll()).thenReturn(List.of(peopleManagement));
        when(mapper.map(any(), any())).thenReturn(dto);

        List<PeopleManagementDto> response = service.read();

        assertNotNull(response);
        assertEquals(NAME, response.get(0).getName());
        assertEquals(convertToDate(BIRTHDAY), response.get(0).getBirthday());
        assertEquals(GENDER, response.get(0).getGender());
        assertEquals(PARENTAGE, response.get(0).getParentage());
    }

    @Test
    @DisplayName("[UPDATE] Should be return a update people")
    void testUpdatePeople() throws ParseException {
        var request = PeopleManagementRequest.builder()
                .name("Teste2")
                .birthday("02-01-2023")
                .gender("F")
                .parentage("Mother")
                .build();

        when(repository.findById(anyLong())).thenReturn(Optional.of(peopleManagement));
        when(mapper.map(any(), any())).thenReturn(dto);

        PeopleManagementDto response = service.update(ID, request);

        assertNotNull(response);
        assertNotEquals(request.getName(), response.getName());
        assertNotEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertNotEquals(request.getGender(), response.getGender());
        assertNotEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("[DELETE] Should be return success after delete a people")
    void testDeletePeople() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(peopleManagement));
        assertDoesNotThrow(() -> service.delete(ID));
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testPeopleAlreadyExists() {
        when(repository.findByNameAndGenderAndParentage(anyString(), anyString(), anyString()))
                .thenReturn(peopleManagement);
        assertThrows(DataIntegratyViolationException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in read method")
    void testReadPeopleNull() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.read());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in update method")
    void testUpdatePeopleNull() throws ParseException {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataEmptyOrNullException.class, () -> service.update(anyLong(), request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in delete method")
    void testDeletePeopleNull() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataEmptyOrNullException.class, () -> service.delete(anyLong()));
    }

    @Test
    @DisplayName("Should be return ParseException in convertToDomain method")
    void testDateParseException1() throws ParseException {
        var request = buildResquest();
        assertThrows(ParseException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Should be return ParseException in updatePeople method")
    void testDateParseException2() throws ParseException {
        var request = buildResquest();
        when(repository.findById(anyLong())).thenReturn(Optional.of(peopleManagement));
        assertThrows(ParseException.class, () -> service.update(ID, request));
    }

    private PeopleManagementRequest buildResquest() {
        return PeopleManagementRequest.builder()
                .name("Teste2")
                .birthday("A")
                .gender("F")
                .parentage("Mother")
                .build();
    }
}