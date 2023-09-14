package com.fiap.gregory.smarthome.domain.services;

import com.fiap.gregory.smarthome.app.request.ApplianceRequest;
import com.fiap.gregory.smarthome.domain.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.domain.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.domain.usecases.HomeApplianceUseCase;
import com.fiap.gregory.smarthome.infra.db.models.HomeAppliance;
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
class HomeApplianceServiceTest {
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
    private HomeApplianceService service;

    @Mock
    private HomeApplianceUseCase useCase;

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
    @DisplayName("[CREATE] Should be created an home appliance")
    void testCreate() {}

    @Test
    @DisplayName("[READ] Should be return a list of the home appliance")
    void testRead() {}

    @Test
    @DisplayName("[UPDADE] Should be update an home appliance")
    void testUpdate() {}

    @Test
    @DisplayName("[DELETE] Should be deleted an home appliance")
    void testDelete() {}
}