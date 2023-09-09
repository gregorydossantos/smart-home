package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.util.List;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PeopleManagementServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String BIRTHDAY = "28-12-1989";
    private static final String GENDER = "M";
    private static final String PARENTAGE = "Father";

    private PeopleManagementRequest request;
    private PeopleManagement peopleManagement;

    @Inject
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

        peopleManagement = PeopleManagement.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        repository.save(peopleManagement);
    }

    @Test
    @DisplayName("Should be return success after create a people")
    void testCreatePeopleSuccess() throws ParseException {
        PeopleManagementDto response = service.create(request);

        assertNotNull(response);
        assertEquals(PeopleManagementDto.class, response.getClass());
        assertEquals(2, response.getId());
        assertEquals(request.getName(), response.getName());
        assertEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("Should be return a list with all the people")
    void testGetListAllPeople() {
        List<PeopleManagementDto> response = service.read();

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should be return a update people")
    void testUpdatePeople() throws ParseException {
        PeopleManagementDto response = service.update(ID, request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("Should be return success after delete a people")
    void testDeletePeople() {
        when(repository.save(any())).thenReturn(peopleManagement);

        service.delete(peopleManagement.getId());

        var people = repository.findById(peopleManagement.getId());

        assertTrue(people.isEmpty());
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testPeopleAlreadyExists() {
        when(repository.findByNameAndGenderAndParentage(anyString(), anyString(), anyString()))
                .thenReturn(peopleManagement);

        assertThrows(DataIntegratyViolationException.class, () -> {
            service.create(any());
        });
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException")
    void testListPeopleNullable() {
        assertThrows(DataEmptyOrNullException.class, () -> {
            service.read();
        });
    }
}