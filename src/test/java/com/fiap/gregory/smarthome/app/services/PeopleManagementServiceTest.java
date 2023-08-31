package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class PeopleManagementServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String BIRTHDAY = "1989-28-12";
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

        repository.save(peopleManagement);
    }

    @Test
    @DisplayName("Should be return success after create a people")
    void testCreatePeopleSuccess() {
        PeopleManagementDto response = service.create(request);

        assertNotNull(response);
        assertEquals(PeopleManagementDto.class, response.getClass());
        assertEquals(peopleManagement.getId(), response.getId());
        assertEquals(peopleManagement.getName(), response.getName());
        assertEquals(peopleManagement.getBirthday(), response.getBirthday());
        assertEquals(peopleManagement.getGender(), response.getGender());
        assertEquals(peopleManagement.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("Should be return a list with all the people")
    void testGetListAllPeople() throws ParseException {
        var peopleManagement = PeopleManagement.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        repository.save(peopleManagement);
        List<PeopleManagementDto> response = service.read();

        assertNotNull(response);
    }

    @Test
    @DisplayName("Should be return a update people")
    void testUpdatePeople() {
        var request = PeopleManagementRequest.builder()
                .name("Gregory")
                .birthday("1989-04-05")
                .gender("M")
                .parentage("Son")
                .build();

        PeopleManagementDto response = service.update(ID, request);

        assertNotNull(response);
        assertNotEquals(dto.getName(), response.getName());
        assertNotEquals(dto.getBirthday(), response.getBirthday());
        assertNotEquals(dto.getGender(), response.getName());
        assertNotEquals(dto.getParentage(), response.getName());
    }

    @Test
    @DisplayName("Should be return success after delete a people")
    void testDeletePeople() {
        service.delete(ID);

        var people = repository.findById(ID);

        assertTrue(people.isEmpty());
    }

    @Test
    @DisplayName("Should be return BadRequestViolationException")
    void testRequestNullOrEmpty() {
        when(repository.save(any())).thenReturn(peopleManagement);

        assertThrows(BadRequestViolationException.class, () -> {
            service.create(any());
        });
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testAddressAlreadyExists() {
        when(repository.findByNameAndGenderAndParentage(anyString(), anyString(), anyString()))
                .thenReturn(Optional.ofNullable(peopleManagement));

        assertThrows(DataIntegratyViolationException.class, () -> {
            service.create(request);
        });
    }
}