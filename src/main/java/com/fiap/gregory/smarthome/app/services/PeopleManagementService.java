package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.convertToDate;

@Service
public class PeopleManagementService {

    private static final String PEOPLE_ALREADY_EXISTS = "Pessoa já cadastrada!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";

    @Autowired
    private PeopleManagementRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUseful validator;

    public PeopleManagementDto create(PeopleManagementRequest request) {
        validator.validateRequest(request);

        existsHomeAppliance(request);
        var peopleManagement = repository.save(convertToDomain(request));

        return mapper.map(peopleManagement, PeopleManagementDto.class);
    }

    private void existsHomeAppliance(PeopleManagementRequest request) {
        var peopleManagement = repository.findByNameAndActive(request.getName(), request.getActive());
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
                .active(request.getActive())
                .build();
    }
}
