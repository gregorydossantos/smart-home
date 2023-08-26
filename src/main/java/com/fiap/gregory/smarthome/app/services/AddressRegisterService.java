package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.AddressRegister;
import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.repositories.AddressRegisterRepository;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.app.useful.StringUseful;
import com.fiap.gregory.smarthome.app.useful.ValidationUseful;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.isNullOrEmpty;

@Service
public class AddressRegisterService {

    private static final String ADDRESS_ALREADY_EXISTS = "Endereço já cadastrado!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";
    private static final String DATA_EMPTY_OR_NULL = "Nenhum endereço encontrado!";
    private static final String REGISTER_NOT_FOUND = "Endereço inesxistente!";

    @Autowired
    private AddressRegisterRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUseful validator;

    public AddressRegisterDto create(AddressRegisterRequest request) {
        validator.validateRequest(request);

        findByStreetAndNumber(request);
        var address = repository.save(toDomain(request));

        return mapper.map(address, AddressRegisterDto.class);
    }

    public List<AddressRegisterDto> read() {
        var addressList = repository.findAll();

        if (addressList.isEmpty()) {
            throw new DataEmptyOrNullException(DATA_EMPTY_OR_NULL);
        }

        addressList.forEach(addressRegister -> convertToPeopleDto(addressRegister.getPeopleManagement()));

        return addressList.stream().map(addressRegister -> mapper.map(addressRegister, AddressRegisterDto.class))
                .collect(Collectors.toList());
    }

    public AddressRegisterDto update(Long id, AddressRegisterRequest request) {
        validator.validateRequest(request);

        var address = repository.findById(id);

        var addressChange = updateAddress(address, request);

        return mapper.map(addressChange, AddressRegisterDto.class);
    }

    public void delete(Long id) {
        if (StringUseful.isNullOrEmpty(id)) {
            throw new BadRequestViolationException(BAD_REQUEST);
        }

        var address = repository.findById(id);
        if (address.isEmpty()) {
            throw new DataEmptyOrNullException(DATA_EMPTY_OR_NULL);
        }

        repository.delete(address.get());
    }

    private void findByStreetAndNumber(AddressRegisterRequest request) {
        var address = repository.findByStreetAndNumber(request.getStreet(), Integer.valueOf(request.getNumber()));
        if (!isNullOrEmpty(address)) {
            throw new DataIntegratyViolationException(ADDRESS_ALREADY_EXISTS);
        }
    }

    private AddressRegister toDomain(AddressRegisterRequest request) {
        return AddressRegister.builder()
                .street(request.getStreet())
                .number(Integer.valueOf(request.getNumber()))
                .district(request.getDistrict())
                .city(request.getCity())
                .state(request.getState())
                .build();
    }

    private AddressRegister updateAddress(Optional<AddressRegister> address, AddressRegisterRequest request) {
        if (address.isEmpty()) {
            throw new DataEmptyOrNullException(REGISTER_NOT_FOUND);
        }

        var addressChange = address.get();
        addressChange.setStreet(request.getStreet());
        addressChange.setNumber(Integer.parseInt(request.getNumber()));
        addressChange.setDistrict(request.getDistrict());
        addressChange.setCity(request.getCity());
        addressChange.setState(addressChange.getState());
        repository.saveAndFlush(addressChange);

        return addressChange;
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
