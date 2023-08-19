package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
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

import java.text.ParseException;
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
    private static final String ACTIVE = "S";

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

    private void mockDatas() throws ParseException {
        request = PeopleManagementRequest.builder()
                .name(NAME)
                .birthday(BIRTHDAY)
                .gender(GENDER)
                .parentage(PARENTAGE)
                .active(ACTIVE)
                .build();

        dto = PeopleManagementDto.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .active(ACTIVE)
                .build();

        peopleManagement = PeopleManagement.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .active(ACTIVE)
                .build();
    }

    @Test
    @DisplayName("Should be return success")
    void testPeopleSuccess() {
        when(repository.save(any())).thenReturn(peopleManagement);
        when(mapper.map(any(), any())).thenReturn(dto);

        PeopleManagementDto response = service.create(request);

        assertNotNull(response);
        assertEquals(AddressRegisterDto.class, response.getClass());
        assertEquals(peopleManagement.getId(), response.getId());
        assertEquals(peopleManagement.getName(), response.getName());
        assertEquals(peopleManagement.getBirthday(), response.getBirthday());
        assertEquals(peopleManagement.getGender(), response.getGender());
        assertEquals(peopleManagement.getParentage(), response.getParentage());
        assertEquals(peopleManagement.getActive(), response.getActive());
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
        when(repository.findByNameAndActive(anyString(), anyString()))
                .thenReturn(Optional.ofNullable(peopleManagement));

        assertThrows(DataIntegratyViolationException.class, () -> {
            service.create(request);
        });
    }
}