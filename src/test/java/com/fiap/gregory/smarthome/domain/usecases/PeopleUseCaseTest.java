package com.fiap.gregory.smarthome.domain.usecases;

import com.fiap.gregory.smarthome.app.request.PeopleRequest;
import com.fiap.gregory.smarthome.domain.dtos.PeopleDto;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.domain.useful.ValidationUseful;
import com.fiap.gregory.smarthome.infra.db.models.People;
import com.fiap.gregory.smarthome.infra.db.repositories.PeopleRepository;
import org.junit.jupiter.api.AfterEach;
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
class PeopleUseCaseTest {
    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String BIRTHDAY = "01-01-2023";
    private static final String GENDER = "M";
    private static final String PARENTAGE = "Father";

    private PeopleRequest request;

    private PeopleDto dto;

    private People people;

    @InjectMocks
    private PeopleUseCase useCase;

    @Mock
    private PeopleRepository repository;

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
        request = PeopleRequest.builder()
                .name(NAME)
                .birthday(BIRTHDAY)
                .gender(GENDER)
                .parentage(PARENTAGE)
                .build();

        dto = PeopleDto.builder()
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
    @DisplayName("[CREATE] Should be return success after create a people")
    void testCreatePeopleSuccess() throws ParseException {
        when(repository.findByNameAndGenderAndParentage(anyString(), anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(people);
        when(mapper.map(any(), any())).thenReturn(dto);

        var response = useCase.create(request);

        assertNotNull(response);
        assertEquals(PeopleDto.class, response.getClass());
        assertEquals(request.getName(), response.getName());
        assertEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertEquals(request.getGender(), response.getGender());
        assertEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("[READ] Should be return a list with all the people")
    void testReadPeople() throws ParseException {
        when(repository.findAll()).thenReturn(List.of(people));
        when(mapper.map(any(), any())).thenReturn(dto);

        List<PeopleDto> response = useCase.read();

        assertNotNull(response);
        assertEquals(NAME, response.get(0).getName());
        assertEquals(convertToDate(BIRTHDAY), response.get(0).getBirthday());
        assertEquals(GENDER, response.get(0).getGender());
        assertEquals(PARENTAGE, response.get(0).getParentage());
    }

    @Test
    @DisplayName("[UPDATE] Should be return a update people")
    void testUpdatePeople() throws ParseException {
        var request = PeopleRequest.builder()
                .name("Teste2")
                .birthday("02-01-2023")
                .gender("F")
                .parentage("Mother")
                .build();

        when(repository.findById(anyLong())).thenReturn(Optional.of(people));
        when(mapper.map(any(), any())).thenReturn(dto);

        PeopleDto response = useCase.update(ID, request);

        assertNotNull(response);
        assertNotEquals(request.getName(), response.getName());
        assertNotEquals(convertToDate(request.getBirthday()), response.getBirthday());
        assertNotEquals(request.getGender(), response.getGender());
        assertNotEquals(request.getParentage(), response.getParentage());
    }

    @Test
    @DisplayName("[DELETE] Should be return success after delete a people")
    void testDeletePeople() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(people));
        assertDoesNotThrow(() -> useCase.delete(ID));
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testPeopleAlreadyExists() {
        when(repository.save(any())).thenReturn(people);
        when(repository.findByNameAndGenderAndParentage(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(people));
        assertThrows(DataIntegratyViolationException.class, () -> useCase.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in read method")
    void testReadPeopleNull() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.read());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in update method")
    void testUpdatePeopleNull() throws ParseException {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataEmptyOrNullException.class, () -> useCase.update(anyLong(), request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException in delete method")
    void testDeletePeopleNull() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(DataEmptyOrNullException.class, () -> useCase.delete(anyLong()));
    }

    @Test
    @DisplayName("Should be return ParseException in convertToDomain method")
    void testDateParseException1() throws ParseException {
        var request = buildResquest();
        assertThrows(ParseException.class, () -> useCase.create(request));
    }

    @Test
    @DisplayName("Should be return ParseException in updatePeople method")
    void testDateParseException2() throws ParseException {
        var request = buildResquest();
        when(repository.findById(anyLong())).thenReturn(Optional.of(people));
        assertThrows(ParseException.class, () -> useCase.update(ID, request));
    }

    private PeopleRequest buildResquest() {
        return PeopleRequest.builder()
                .name("Teste2")
                .birthday("A")
                .gender("F")
                .parentage("Mother")
                .build();
    }
}