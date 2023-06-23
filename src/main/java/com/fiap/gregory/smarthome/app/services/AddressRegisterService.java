package com.fiap.gregory.smarthome.app.services;

import com.fiap.gregory.smarthome.app.models.domains.AddressRegister;
import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.repositories.AddressRegisterRepository;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.exceptions.BadRequestViolationException;
import com.fiap.gregory.smarthome.app.services.exceptions.DataIntegratyViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fiap.gregory.smarthome.app.useful.StringUseful.isNullOrEmpty;

@Service
public class AddressRegisterService {

    private static final String ADDRESS_ALREADY_EXISTS = "Endereço já cadastrado!";
    private static final String BAD_REQUEST = "Erro no contexto da requisição!";

    @Autowired
    private AddressRegisterRepository repository;

    @Autowired
    private ModelMapper mapper;

    public AddressRegisterDto create(AddressRegisterRequest request) {
        if (isNullOrEmpty(request)) {
            throw new BadRequestViolationException(BAD_REQUEST);
        }

        findByStreetAndNumber(request);
        var address = repository.save(toDomain(request));

        return mapper.map(address, AddressRegisterDto.class);
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
}
