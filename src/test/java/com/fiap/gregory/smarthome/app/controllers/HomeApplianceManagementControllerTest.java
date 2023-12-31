package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
import com.fiap.gregory.smarthome.app.services.HomeApplianceManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class HomeApplianceManagementControllerTest {

    private HomeApplianceManagementRequest request;
    private HomeApplianceManagementDto homeApplianceManagementDto;

    @InjectMocks
    private HomeApplianceManagementController controller;

    @Mock
    private HomeApplianceManagementService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockDatas();
    }

    private void mockDatas() {
        homeApplianceManagementDto = HomeApplianceManagementDto.builder()
                .id(1L)
                .name("Teste")
                .model("Teste")
                .brand("Teste")
                .voltage(110)
                .build();

        request = HomeApplianceManagementRequest.builder()
                .name("Teste")
                .model("Teste")
                .brand("Teste")
                .voltage("110")
                .build();
    }

    @Test
    @DisplayName("Should be return a Http status 201 - Created")
    void createHomeApplianceWithSuccess() {
        when(service.create(any())).thenReturn(homeApplianceManagementDto);

        ResponseEntity<HomeApplianceManagementDto> response = controller.create(request);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}