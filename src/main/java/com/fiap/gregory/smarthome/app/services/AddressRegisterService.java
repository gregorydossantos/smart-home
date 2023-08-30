package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.AddressRegister;
import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.repositories.AddressRegisterRepository;
import com.fiap.gregory.smarthome.app.repositories.PeopleManagementRepository;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.isNullOrEmpty;

@Service
public class AddressRegisterService {

    private static final String ADDRESS_ALREADY_EXISTS = "Endereço já cadastrado!";
    private static final String DATA_EMPTY_OR_NULL = "Nenhum endereço encontrado!";
    private static final String ADDRESS_NOT_FOUND = "Endereço inesxistente!";
    private static final String PEOPLE_NOT_FOUND = "Pessoa não encontrada!";

    @Autowired
    private AddressRegisterRepository repository;

    @Autowired
    private PeopleManagementRepository peopleRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUseful validator;

    public AddressRegisterDto create(AddressRegisterRequest request) {
        validator.validateRequest(request);

        addressExists(request);
        var people = peopleExists(Long.valueOf(request.getPeopleId()));
        var address = repository.save(toDomain(request, people));

        return mapper.map(address, AddressRegisterDto.class);
    }

    public List<AddressRegisterDto> read() {
        var addressList = repository.findAll();

        if (addressList.isEmpty()) {
            throw new DataEmptyOrNullException(ADDRESS_NOT_FOUND);
        }

        return addressList.stream().map(addressRegister -> mapper.map(addressRegister, AddressRegisterDto.class))
                .collect(Collectors.toList());
    }

    public AddressRegisterDto update(Long id, AddressRegisterRequest request) {
        validator.validateRequest(request);

        var address = repository.findById(id);

        if (address.isEmpty()) {
            throw new DataEmptyOrNullException(ADDRESS_NOT_FOUND);
        }

        var people = peopleExists(Long.valueOf(request.getPeopleId()));
        var addressChange = updateAddress(address.get(), request, people);

        return mapper.map(addressChange, AddressRegisterDto.class);
    }

    public void delete(Long id) {
        var address = repository.findById(id);
        if (address.isEmpty()) {
            throw new DataEmptyOrNullException(DATA_EMPTY_OR_NULL);
        }

        repository.delete(address.get());
    }

    private void addressExists(AddressRegisterRequest request) {
        var address = repository.findByStreetAndDistrictAndCity(request.getStreet(), request.getDistrict(),
                request.getCity());
        if (!isNullOrEmpty(address)) {
            throw new DataIntegratyViolationException(ADDRESS_ALREADY_EXISTS);
        }
    }

    private AddressRegister toDomain(AddressRegisterRequest request, PeopleManagement people) {
        return AddressRegister.builder()
                .street(request.getStreet())
                .number(Integer.parseInt(request.getNumber()))
                .district(request.getDistrict())
                .city(request.getCity())
                .state(request.getState())
                .peopleManagement(PeopleManagement.builder()
                        .id(people.getId())
                        .name(people.getName())
                        .birthday(people.getBirthday())
                        .gender(people.getGender())
                        .parentage(people.getParentage())
                        .build())
                .build();
    }

    private AddressRegister updateAddress(AddressRegister address, AddressRegisterRequest request, PeopleManagement people) {
        address.setStreet(request.getStreet());
        address.setNumber(Integer.parseInt(request.getNumber()));
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPeopleManagement(people);
        repository.saveAndFlush(address);

        return address;
    }

    private PeopleManagement peopleExists(Long id) {
        var people = peopleRepository.findById(id);
        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }
        return people.get();
    }
}
