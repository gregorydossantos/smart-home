package com.fiap.gregory.smarthome.domain.usecases;

import com.fiap.gregory.smarthome.app.request.ApplianceRequest;
import com.fiap.gregory.smarthome.domain.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.domain.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.domain.useful.ValidationUseful;
import com.fiap.gregory.smarthome.infra.db.models.HomeAppliance;
import com.fiap.gregory.smarthome.infra.db.models.People;
import com.fiap.gregory.smarthome.infra.db.repositories.HomeApplianceRepository;
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
class HomeApplianceUseCaseTest {
    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String MODEL = "Teste";
    private static final String BRAND = "Testelandia";
    private static final Integer VOLTAGE = 110;

    private ApplianceRequest request;

    private HomeApplianceManagementDto dto;

    private HomeAppliance homeAppliance;

    private People people;

    @InjectMocks
    private HomeApplianceUseCase useCase;

    @Mock
    private HomeApplianceRepository repository;

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
        request = ApplianceRequest.builder()
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
                .peopleManagement(PeopleManagementDto.builder()
                        .id(1L)
                        .name("Teste")
                        .birthday(convertToDate("01-01-2023"))
                        .gender("M")
                        .parentage("Father")
                        .build())
                .build();

        homeAppliance = HomeAppliance.builder()
                .id(ID)
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage(VOLTAGE)
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
    @DisplayName("[CREATE] Should be return success after create a home appliance")
    void testCreateHomeAppliance() {
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(repository.save(any())).thenReturn(homeAppliance);
        when(mapper.map(any(), any())).thenReturn(dto);

        HomeApplianceManagementDto response = useCase.create(request);

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

        List<HomeApplianceManagementDto> response = useCase.read();
        assertNotNull(response);
    }

    @Test
    @DisplayName("[UPDATE] Should be return a update home appliance")
    void testUpdateHomeAppliance() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(homeAppliance));
        when(peopleRepository.findById(anyLong())).thenReturn(Optional.of(people));
        when(mapper.map(any(), any())).thenReturn(dto);

        var request = ApplianceRequest.builder()
                .name("name")
                .model("model")
                .brand("brand")
                .voltage("220")
                .peopleId("1")
                .build();

        HomeApplianceManagementDto response = useCase.update(ID, request);

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
        assertDoesNotThrow(() -> useCase.delete(ID));
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException when call existsHomeAppliance method")
    void testHomeApplianceAlreadyExists() {
        when(repository.findByNameAndModelAndBrand(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(homeAppliance));
        assertThrows(DataIntegratyViolationException.class, () -> useCase.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call peopleExists method")
    void testPeopleAlreadyExists() {
        assertThrows(DataEmptyOrNullException.class, () -> useCase.create(request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call read method")
    void testReadHomeApplianceNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.read());
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call update method")
    void testUpdateHomeApplianceNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.update(ID, request));
    }

    @Test
    @DisplayName("Should be return DataEmptyOrNullException when call delete method")
    void testDeleteHomeApplianceNotFound() {
        repository.deleteAll();
        assertThrows(DataEmptyOrNullException.class, () -> useCase.delete(ID));
    }

    @Test
    @DisplayName("Should be return NumberFormatException when call peopleExists method")
    void testPeopleExists() {
        var request = ApplianceRequest.builder()
                .name("name")
                .model("model")
                .brand("brand")
                .voltage("220")
                .peopleId("A")
                .build();
        assertThrows(NumberFormatException.class, () -> useCase.create(request));
    }
}