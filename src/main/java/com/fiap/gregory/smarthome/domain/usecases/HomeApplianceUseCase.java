package com.fiap.gregory.smarthome.domain.usecases;

import com.fiap.gregory.smarthome.app.request.ApplianceRequest;
import com.fiap.gregory.smarthome.domain.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.domain.useful.ValidationUseful;
import com.fiap.gregory.smarthome.infra.db.models.HomeAppliance;
import com.fiap.gregory.smarthome.infra.db.models.People;
import com.fiap.gregory.smarthome.infra.db.repositories.HomeApplianceRepository;
import com.fiap.gregory.smarthome.infra.db.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.APPLIANCE_ALREADY_EXISTS;
import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.APPLIANCE_NOT_FOUND;
import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.PEOPLE_NOT_FOUND;
import static com.fiap.gregory.smarthome.domain.useful.StringUseful.convertToInt;

@Service
@AllArgsConstructor
public class HomeApplianceUseCase {

    final HomeApplianceRepository repository;
    final PeopleRepository peopleRepository;
    final ModelMapper mapper;
    final ValidationUseful validator;

    public HomeApplianceManagementDto create(ApplianceRequest request) {
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

        return homeApplianceManagementList.stream().map(homeAppliance ->
                        mapper.map(homeAppliance, HomeApplianceManagementDto.class))
                .collect(Collectors.toList());
    }

    public HomeApplianceManagementDto update(Long id, ApplianceRequest request) {
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

    private void existsHomeAppliance(ApplianceRequest request) {
        var homeApplianceManagement = repository.findByNameAndModelAndBrand(request.getName(), request.getModel(),
                request.getBrand());
        if (homeApplianceManagement.isPresent()) {
            throw new DataIntegratyViolationException(APPLIANCE_ALREADY_EXISTS);
        }
    }

    @SneakyThrows
    private HomeAppliance convertToDomain(ApplianceRequest request) {
        var people = peopleExists(Long.parseLong(request.getPeopleId()));

        return HomeAppliance.builder()
                .name(request.getName())
                .model(request.getModel())
                .brand(request.getBrand())
                .voltage(convertToInt(request.getVoltage()))
                .people(people)
                .build();
    }

    private HomeAppliance updateHomeAppliance(HomeAppliance homeAppliance, ApplianceRequest request) {
        var people = peopleExists(Long.parseLong(request.getPeopleId()));

        homeAppliance.setName(request.getName());
        homeAppliance.setBrand(request.getBrand());
        homeAppliance.setModel(request.getModel());
        homeAppliance.setVoltage(Integer.parseInt(request.getVoltage()));
        homeAppliance.setPeople(people);
        repository.saveAndFlush(homeAppliance);

        return homeAppliance;
    }

    private People peopleExists(Long id) {
        var people = peopleRepository.findById(id);
        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }
        return people.get();
    }
}
