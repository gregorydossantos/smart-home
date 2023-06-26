package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.HomeApplianceManagement;
import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.repositories.HomeApplianceManagementRepository;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class HomeApplianceManagementServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Teste";
    private static final String MODEL = "Teste";
    private static final String BRAND = "Testelandia";
    private static final Integer VOLTAGE = 110;

    private HomeApplianceManagementRequest request;
    private HomeApplianceManagementDto dto;
    private HomeApplianceManagement homeAppliance;

    @InjectMocks
    private HomeApplianceManagementService service;

    @Mock
    private HomeApplianceManagementRepository repository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() {
        request = HomeApplianceManagementRequest.builder()
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage("110")
                .build();

        dto = HomeApplianceManagementDto.builder()
                .id(ID)
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage(VOLTAGE)
                .build();

        homeAppliance = HomeApplianceManagement.builder()
                .id(ID)
                .name(NAME)
                .model(MODEL)
                .brand(BRAND)
                .voltage(VOLTAGE)
                .build();
    }

    @Test
    @DisplayName("Should be return success")
    void testHomeApplianceSuccess() {
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
    @DisplayName("Should be return BadRequestViolationException")
    void testRequestNullOrEmpty() {
        when(repository.save(any())).thenReturn(homeAppliance);

        assertThrows(BadRequestViolationException.class, () -> {
            service.create(any());
        });
    }

    @Test
    @DisplayName("Should be return DataIntegratyViolationException")
    void testAddressAlreadyExists() {
        when(repository.findByNameAndModelAndBrand(anyString(), anyString(), anyString()))
                .thenReturn(Optional.ofNullable(homeAppliance));

        assertThrows(DataIntegratyViolationException.class, () -> {
            service.create(request);
        });
    }
}