package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.HomeApplianceManagement;
import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.repositories.HomeApplianceManagementRepository;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToInt;

@Service
public class HomeApplianceManagementService {

    private static final String APPLIANCE_ALREADY_EXISTS = "Eletrodoméstico já cadastrado!";
    private static final String APPLIANCE_NOT_FOUND = "Eletrodoméstico não encontrado!";
    private static final String PEOPLE_NOT_FOUND = "Pessoa não encontrada!";

    @Autowired
    private HomeApplianceManagementRepository repository;

    @Autowired
    private PeopleManagementRepository peopleRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUseful validator;

    public HomeApplianceManagementDto create(HomeApplianceManagementRequest request) {
        validator.validateRequest(request);

        existsHomeAppliance(request);
        var homeAppliance = repository.save(convertToDomain(request));

        return mapper.map(homeAppliance, HomeApplianceManagementDto.class);
    }

    public List<HomeApplianceManagementDto> read() {
        var homeApplianceManagementList = repository.findAll();

        if (homeApplianceManagementList.isEmpty()) {
            throw new DataEmptyOrNullException(APPLIANCE_NOT_FOUND);
        }

        return homeApplianceManagementList.stream().map(homeApplianceManagement ->
                        mapper.map(homeApplianceManagement, HomeApplianceManagementDto.class))
                .collect(Collectors.toList());
    }

    public HomeApplianceManagementDto update(Long id, HomeApplianceManagementRequest request) {
        validator.validateRequest(request);

        var homeAppliance = repository.findById(id);
        if (homeAppliance.isEmpty()) {
            throw new DataEmptyOrNullException(APPLIANCE_NOT_FOUND);
        }

        var homeApplianceManagementChange = updateHomeAppliance(homeAppliance.get(), request);

        return mapper.map(homeApplianceManagementChange, HomeApplianceManagementDto.class);
    }

    public void delete(Long id) {
        var homeApplianceManagement = repository.findById(id);
        if (homeApplianceManagement.isEmpty()) {
            throw new DataEmptyOrNullException(APPLIANCE_NOT_FOUND);
        }

        repository.delete(homeApplianceManagement.get());
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
        var people = peopleExists(Long.parseLong(request.getPeopleId()));

        return HomeApplianceManagement.builder()
                .name(request.getName())
                .model(request.getModel())
                .brand(request.getBrand())
                .voltage(convertToInt(request.getVoltage()))
                .peopleManagement(people)
                .build();
    }

    private HomeApplianceManagement updateHomeAppliance(HomeApplianceManagement homeAppliance, HomeApplianceManagementRequest request) {
        var people = peopleExists(Long.parseLong(request.getPeopleId()));

        homeAppliance.setName(request.getName());
        homeAppliance.setBrand(request.getBrand());
        homeAppliance.setModel(request.getModel());
        homeAppliance.setVoltage(Integer.parseInt(request.getVoltage()));
        homeAppliance.setPeopleManagement(people);
        repository.saveAndFlush(homeAppliance);

        return homeAppliance;
    }

    private PeopleManagement peopleExists(Long id) {
        var people = peopleRepository.findById(id);
        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }
        return people.get();
    }
}
