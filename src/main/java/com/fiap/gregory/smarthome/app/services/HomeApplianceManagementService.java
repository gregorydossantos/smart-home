package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.HomeApplianceManagement;
import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.HomeApplianceManagementRepository;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.StringUseful;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToInt;

@Service
public class HomeApplianceManagementService {

    private static final String APPLIANCE_ALREADY_EXISTS = "Eletrodoméstico já cadastrado!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";
    private static final String DATA_EMPTY_OR_NULL = "Nenhum endereço encontrado!";
    private static final String REGISTER_NOT_FOUND = "Endereço inesxistente!";

    @Autowired
    private HomeApplianceManagementRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUseful validator;

    public HomeApplianceManagementDto create(HomeApplianceManagementRequest request) {
        validator.validateRequest(request);

        existsHomeAppliance(request);
        var homeApplianceManagement = repository.save(convertToDomain(request));

        return mapper.map(homeApplianceManagement, HomeApplianceManagementDto.class);
    }

    public List<HomeApplianceManagementDto> read() {
        var homeApplianceManagementList = repository.findAll();

        if (homeApplianceManagementList.isEmpty()) {
            throw new DataEmptyOrNullException(DATA_EMPTY_OR_NULL);
        }

        homeApplianceManagementList.forEach(homeApplianceManagement ->
                convertToPeopleDto(homeApplianceManagement.getPeopleManagement()));

        return homeApplianceManagementList.stream().map(homeApplianceManagement ->
                        mapper.map(homeApplianceManagement, HomeApplianceManagementDto.class))
                .collect(Collectors.toList());
    }

    public HomeApplianceManagementDto update(Long id, HomeApplianceManagementRequest request) {
        validator.validateRequest(request);

        var homeApplianceManagement = repository.findById(id);

        var homeApplianceManagementChange = updateHomeAppliance(homeApplianceManagement, request);

        return mapper.map(homeApplianceManagementChange, HomeApplianceManagementDto.class);
    }

    public void delete(Long id) {
        if (StringUseful.isNullOrEmpty(id)) {
            throw new BadRequestViolationException(BAD_REQUEST);
        }

        var homeApplianceManagement = repository.findById(id);
        if (homeApplianceManagement.isEmpty()) {
            throw new DataEmptyOrNullException(DATA_EMPTY_OR_NULL);
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
        return HomeApplianceManagement.builder()
                .name(request.getName())
                .model(request.getModel())
                .brand(request.getBrand())
                .voltage(convertToInt(request.getVoltage()))
                .build();
    }

    private HomeApplianceManagement updateHomeAppliance(Optional<HomeApplianceManagement> homeAppliance, HomeApplianceManagementRequest request) {
        if (homeAppliance.isEmpty()) {
            throw new DataEmptyOrNullException(REGISTER_NOT_FOUND);
        }

        var homeApplianceChange = homeAppliance.get();
        homeApplianceChange.setName(request.getName());
        homeApplianceChange.setBrand(request.getBrand());
        homeApplianceChange.setModel(request.getModel());
        homeApplianceChange.setVoltage(Integer.parseInt(request.getVoltage()));
        repository.saveAndFlush(homeApplianceChange);

        return homeApplianceChange;
    }

    private void convertToPeopleDto(PeopleManagement people) {
        PeopleManagementDto.builder()
                .id(people.getId())
                .name(people.getName())
                .birthday(people.getBirthday())
                .gender(people.getGender())
                .parentage(people.getParentage())
                .active(people.getActive())
                .build();
    }
}
