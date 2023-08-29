package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;

@Service
public class PeopleManagementService {

    private static final String PEOPLE_ALREADY_EXISTS = "Pessoa já cadastrada!";
    private static final String PEOPLE_NOT_FOUND = "Pessoa não encontrada!";

    @Autowired
    private PeopleManagementRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUseful validator;

    public PeopleManagementDto create(PeopleManagementRequest request) {
        validator.validateRequest(request);

        peopleExists(request);
        var peopleManagement = repository.save(convertToDomain(request));

        return mapper.map(peopleManagement, PeopleManagementDto.class);
    }

    public List<PeopleManagementDto> read() {
        var peopleList = repository.findAll();

        if (peopleList.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }

        return peopleList.stream().map(peopleManagement -> mapper.map(peopleManagement, PeopleManagementDto.class))
                .collect(Collectors.toList());
    }

    public PeopleManagementDto update(Long id, PeopleManagementRequest request) {
        validator.validateRequest(request);

        var people = repository.findById(id);

        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }

        var peopleChange = updatePeople(people.get(), request);

        return mapper.map(peopleChange, PeopleManagementDto.class);
    }

    public void delete(Long id) {
        var people = repository.findById(id);
        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }

        repository.delete(people.get());
    }

    private void peopleExists(PeopleManagementRequest request) {
        var peopleManagement = repository.findByNameAndGenderAndParentage(request.getName(), request.getGender(),
                request.getParentage());
        if (peopleManagement.isPresent()) {
            throw new DataIntegratyViolationException(PEOPLE_ALREADY_EXISTS);
        }
    }

    @SneakyThrows
    private PeopleManagement convertToDomain(PeopleManagementRequest request) {
        return PeopleManagement.builder()
                .name(request.getName())
                .birthday(convertToDate(request.getBirthday()))
                .gender(request.getGender())
                .parentage(request.getParentage())
                .build();
    }

    @SneakyThrows
    private PeopleManagement updatePeople(PeopleManagement people, PeopleManagementRequest request) {
        people.setName(request.getName());
        people.setBirthday(convertToDate(request.getBirthday()));
        people.setGender(request.getGender());
        people.setParentage(request.getParentage());
        repository.saveAndFlush(people);

        return people;
    }
}
