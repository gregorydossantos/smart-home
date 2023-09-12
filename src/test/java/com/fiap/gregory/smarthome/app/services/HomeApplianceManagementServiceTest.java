package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.HomeApplianceManagement;
import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.HomeApplianceManagementRepository;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
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
class HomeApplianceManagementServiceTest {
    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String MODEL = "Teste";
    private static final String BRAND = "Testelandia";
    private static final Integer VOLTAGE = 110;

    private HomeApplianceManagementRequest request;

    private HomeApplianceManagementDto dto;

    private HomeApplianceManagement homeAppliance;

    private PeopleManagement people;

    @InjectMocks
    private HomeApplianceManagementService service;

    @Mock
    private HomeApplianceManagementRepository repository;

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
        request = HomeApplianceManagementRequest.builder()
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage("110")
                .peopleId("1")
                .build();

        dto = HomeApplianceManagementDto.builder()
                .id(ID)
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage(VOLTAGE)
                .peopleManagementDto(PeopleManagementDto.builder()
                        .id(1L)
                        .name("Teste")
                        .birthday(convertToDate("01-01-2023"))
                        .gender("M")
                        .parentage("Father")
                        .build())
                .build();

        homeAppliance = HomeApplianceManagement.builder()
                .id(ID)
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage(VOLTAGE)
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
    @DisplayName("[CREATE] Should be return success after create a home appliance")
    void testCreateHomeAppliance() {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(repository.save(any())).thenReturn(homeAppliance);
        when(mapper.map(any(), any())).thenReturn(dto);

        HomeApplianceManagementDto response = service.create(request);

        assertNotNull(response);
        assertEquals(HomeApplianceManagementDto.class, response.getClass());
        assertEquals(homeAppliance.getId(), response.getId());
        assertEquals(homeAppliance.getName(), response.getName());
        assertEquals(homeAppliance.getModel(), response.getModel());
        assertEquals(homeAppliance.getBrand(), response.getBrand());
        assertEquals(homeAppliance.getVoltage(), response.getVoltage());
    }

    @Test
    @DisplayName("[READ] Should be return a home appliance list")
    void testReadHomeAppliance() {
        when(repository.findAll()).thenReturn(List.of(homeAppliance));
        when(mapper.map(any(), any())).thenReturn(dto);

        List<HomeApplianceManagementDto> response = service.read();
        assertNotNull(response);
    }

    @Test
    @DisplayName("[UPDATE] Should be return a update home appliance")
    void testUpdateHomeAppliance() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(homeAppliance));
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(mapper.map(any(), any())).thenReturn(dto);

        var request = HomeApplianceManagementRequest.builder()
                .name("name")
                .model("model")
                .brand("brand")
                .voltage("220")
                .peopleId("1")
                .build();

        HomeApplianceManagementDto response = service.update(ID, request);

        assertNotNull(response);
        assertNotEquals(homeAppliance.getName(), response.getName());
        assertNotEquals(homeAppliance.getModel(), response.getModel());
        assertNotEquals(homeAppliance.getBrand(), response.getBrand());
        assertNotEquals(homeAppliance.getVoltage(), response.getVoltage());
    }

    @Test
    @DisplayName("[DELETE] Should be return success after delete a home appliance")
    void testDeleteHomeAppliance() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(homeAppliance));
        assertDoesNotThrow(() -> service.delete(ID));
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException when call existsHomeAppliance method")
    void testHomeApplianceAlreadyExists() {
        when(repository.findByNameAndModelAndBrand(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(homeAppliance));
        assertThrows(DataIntegratyViolationException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call peopleExists method")
    void testPeopleAlreadyExists() {
        assertThrows(DataEmptyOrNullException.class, () -> service.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call read method")
    void testReadHomeApplianceNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.read());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call update method")
    void testUpdateHomeApplianceNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.update(ID, request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call delete method")
    void testDeleteHomeApplianceNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> service.delete(ID));
    }

    @Test
    @DisplayName("Should be return NumberFormatException when call peopleExists method")
    void testPeopleExists() {
        var request = HomeApplianceManagementRequest.builder()
                .name("name")
                .model("model")
                .brand("brand")
                .voltage("220")
                .peopleId("A")
                .build();
        assertThrows(NumberFormatException.class, () -> service.create(request));
    }
}