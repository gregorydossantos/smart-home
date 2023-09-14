package com.fiap.gregory.smarthome.app.rest;

import com.fiap.gregory.smarthome.app.request.PeopleRequest;
import com.fiap.gregory.smarthome.domain.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.domain.services.PeopleService;
import com.fiap.gregory.smarthome.domain.usecases.PeopleUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.util.List;

import static com.fiap.gregory.smarthome.domain.useful.StringUseful.convertToDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class PeopleControllerTest {

    private final static Long ID = 1L;
    private final static String NAME = "Teste";
    private final static String BIRHDAY = "1989-12-28";
    private final static String GENDER = "M";
    private final static String PARENTAGE = "Father";

    private PeopleRequest request;
    private PeopleManagementDto peopleManagementDto;

    @InjectMocks
    private PeopleController controller;

    @Mock
    private PeopleService service;

    @Mock
    private PeopleUseCase useCase;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() throws ParseException {
        peopleManagementDto = PeopleManagementDto.builder()
                .id(ID)
                .name(NAME)
                .birthday(convertToDate(BIRHDAY))
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        request = PeopleRequest.builder()
                .name(NAME)
                .birthday(BIRHDAY)
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();
    }

    @Test
    @DisplayName("Should be return a Http status 201 - Created")
    void testCreateAddressWithSuccess() {
        when(service.create(any())).thenReturn(peopleManagementDto);

        ResponseEntity<PeopleManagementDto> response = controller.create(request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return a list with all the people")
    void testGetAListAllPeople() {
        ResponseEntity<List<PeopleManagementDto>> response = controller.readAll();

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be return update people")
    void testUpdatePeopleSuccess() {
        when(service.update(anyLong(), any())).thenReturn(peopleManagementDto);

        ResponseEntity<PeopleManagementDto> response = controller.update(ID, request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should be delete people")
    void testDeletePeopleSuccess() {
        controller.delete(ID);

        assertEquals(HttpStatus.OK.value(), 200);
    }
}