package com.fiap.gregory.smarthome.domain.usecases;

import com.fiap.gregory.smarthome.app.request.PeopleRequest;
import com.fiap.gregory.smarthome.domain.dtos.PeopleDto;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.domain.useful.ValidationUseful;
import com.fiap.gregory.smarthome.infra.db.models.People;
import com.fiap.gregory.smarthome.infra.db.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.PEOPLE_ALREADY_EXISTS;
import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.PEOPLE_NOT_FOUND;
import static com.fiap.gregory.smarthome.domain.useful.StringUseful.convertToDate;

@Service
@AllArgsConstructor
public class PeopleUseCase {

    final PeopleRepository repository;
    final ModelMapper mapper;
    final ValidationUseful validator;

    public PeopleDto create(PeopleRequest request) {
        validator.validateRequest(request);
        var people = peopleExists(request);
        return mapper.map(people, PeopleDto.class);
    }

    public List<PeopleDto> read() {
        var peopleList = repository.findAll();

        if (peopleList.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }

        return peopleList.stream().map(peopleManagement -> mapper.map(peopleManagement, PeopleDto.class))
                .collect(Collectors.toList());
    }

    public PeopleDto update(Long id, PeopleRequest request) {
        validator.validateRequest(request);

        var people = repository.findById(id);

        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }

        var peopleChange = updatePeople(people.get(), request);

        return mapper.map(peopleChange, PeopleDto.class);
    }

    public void delete(Long id) {
        var people = repository.findById(id);
        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }

        repository.delete(people.get());
    }

    private People peopleExists(PeopleRequest request) {
        var peopleOptional = repository.findByNameAndGenderAndParentage(request.getName(), request.getGender(),
                request.getParentage());

        if (peopleOptional.isPresent()) {
            throw new DataIntegratyViolationException(PEOPLE_ALREADY_EXISTS);
        }

        var people = convertToDomain(request);
        repository.save(people);
        return people;
    }

    @SneakyThrows
    private People convertToDomain(PeopleRequest request) {
        return People.builder()
                .name(request.getName())
                .birthday(convertToDate(request.getBirthday()))
                .gender(request.getGender())
                .parentage(request.getParentage())
                .build();
    }

    @SneakyThrows
    private People updatePeople(People people, PeopleRequest request) {
        people.setName(request.getName());
        people.setBirthday(convertToDate(request.getBirthday()));
        people.setGender(request.getGender());
        people.setParentage(request.getParentage());
        repository.saveAndFlush(people);

        return people;
    }
}
