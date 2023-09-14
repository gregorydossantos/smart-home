package com.fiap.gregory.smarthome.domain.services;

import com.fiap.gregory.smarthome.app.request.PeopleRequest;
import com.fiap.gregory.smarthome.domain.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.domain.usecases.PeopleUseCase;
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
class PeopleServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String BIRTHDAY = "01-01-2023";
    private static final String GENDER = "M";
    private static final String PARENTAGE = "Father";

    private PeopleRequest request;

    private PeopleManagementDto dto;

    private People people;

    @InjectMocks
    private PeopleService service;

    @Mock
    private PeopleUseCase useCase;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() throws ParseException {
        request = PeopleRequest.builder()
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

        people = People.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRTHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();
    }

    @Test
    @DisplayName("[CREATE] Should be created a people")
    void testCreate() {}

    @Test
    @DisplayName("[READ] Should be return a list of the people")
    void testRead() {}

    @Test
    @DisplayName("[UPDADE] Should be update a people")
    void testUpdate() {}

    @Test
    @DisplayName("[DELETE] Should be deleted a people")
    void testDelete() {}
}