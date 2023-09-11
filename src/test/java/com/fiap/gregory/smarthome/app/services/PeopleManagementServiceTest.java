package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.util.List;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
@ActiveProfiles("test")
class PeopleManagementServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String BIRTHDAY = "01-01-2023";
    private static final String GENDER = "M";
    private static final String PARENTAGE = "Father";
    private PeopleManagementRequest request;
    private PeopleManagement peopleManagement;
    @Autowired
    private PeopleManagementService service;
    @Autowired
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
                .name("Gregory")
                .birthday(BIRTHDAY)
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
        var people = PeopleManagement.builder()
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();
        repository.saveAndFlush(people);

        var request = PeopleManagementRequest.builder()
                .name("Teste2")
                .birthday("02-01-2023")
                .gender("F")
                .parentage("Mother")
                .build();

        PeopleManagementDto response = service.update(people.getId(), request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("[DELETE] Should be return success after delete a people")
    void testDeletePeople() throws ParseException {
        var peopleSaved = PeopleManagement.builder()
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();
        repository.saveAndFlush(peopleSaved);

        service.delete(peopleSaved.getId());

        var people = repository.findById(ID);

        assertTrue(people.isEmpty());
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testPeopleAlreadyExists() {
        var request = PeopleManagementRequest.builder()
                .name(NAME)
                .birthday(BIRTHDAY)
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();
        assertThrows(DataIntegratyViolationException.class, () -> {
            service.create(request);
        });
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in read method")
    void testReadPeopleNull() {
        repository.deleteAll();

        assertThrows(DataEmptyOrNullException.class, () -> {
            service.read();
        });
    }

    @Disabled(value = "This test needs to be fixed!")
    @Test
    @DisplayName("Should be return DataEmptyOrNullException in update method")
    void testUpdatePeopleNull() throws ParseException {
        var request = PeopleManagementRequest.builder()
                .name("Teste2")
                .birthday("02-01-2023")
                .gender("F")
                .parentage("Mother")
                .build();

        assertThrows(DataEmptyOrNullException.class, () -> {
            service.update(100L, request);
        });
    }

    @Disabled(value = "This test needs to be fixed!")
    @Test
    @DisplayName("Should be return DataEmptyOrNullException in delete method")
    void testDeletePeopleNull() {
        assertThrows(DataEmptyOrNullException.class, () -> {
            service.delete(anyLong());
        });
    }
}