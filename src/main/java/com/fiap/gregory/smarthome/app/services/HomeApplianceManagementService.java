package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.HomeApplianceManagement;
import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.repositories.HomeApplianceManagementRepository;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.*;

@Service
public class HomeApplianceManagementService {

    private static final String APPLIANCE_ALREADY_EXISTS = "Eletrodoméstico já cadastrado!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";

    @Autowired
    private HomeApplianceManagementRepository repository;

    @Autowired
    private ModelMapper mapper;

    public HomeApplianceManagementDto create(HomeApplianceManagementRequest request) {
        if (isNullOrEmpty(request)) {
            throw new BadRequestViolationException(BAD_REQUEST);
        }

        existsHomeAppliance(request);
        var homeApplianceManagement = repository.save(convertToDomain(request));

        return mapper.map(homeApplianceManagement, HomeApplianceManagementDto.class);
    }

    private void existsHomeAppliance(HomeApplianceManagementRequest request) {
        var homeApplianceManagement = repository.findByNameAndModelAndBrand(request.getName(), request.getModel(),
                request.getBrand());
        if (homeApplianceManagement.isPresent()) {
            throw new DataIntegratyViolationException(APPLIANCE_ALREADY_EXISTS);
        }
    }

    @SneakyThrows
    private HomeApplianceManagement convertToDomain(HomeApplianceManagementRequest request) {
        return HomeApplianceManagement.builder()
                .name(request.getName())
                .model(request.getModel())
                .brand(request.getBrand())
                .voltage(convertToInt(request.getVoltage()))
                .build();
    }
}
