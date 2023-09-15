package com.fiap.gregory.smarthome.domain.usecases;

import com.fiap.gregory.smarthome.app.request.AddressRequest;
import com.fiap.gregory.smarthome.domain.dtos.AddressDto;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataEmptyOrNullException;
import com.fiap.gregory.smarthome.domain.services.exceptions.DataIntegratyViolationException;
import com.fiap.gregory.smarthome.domain.useful.ValidationUseful;
import com.fiap.gregory.smarthome.infra.db.models.Address;
import com.fiap.gregory.smarthome.infra.db.models.People;
import com.fiap.gregory.smarthome.infra.db.repositories.AddressRepository;
import com.fiap.gregory.smarthome.infra.db.repositories.PeopleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.ADDRESS_ALREADY_EXISTS;
import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.ADDRESS_NOT_FOUND;
import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.PEOPLE_NOT_FOUND;
import static com.fiap.gregory.smarthome.domain.useful.StringUseful.isNullOrEmpty;

@Service
@AllArgsConstructor
public class AddressUseCase {

    final AddressRepository repository;
    final PeopleRepository peopleRepository;
    final ModelMapper mapper;
    final ValidationUseful validator;

    public AddressDto create(AddressRequest request) {
        validator.validateRequest(request);

        addressExists(request);
        var people = peopleExists(Long.valueOf(request.getPeopleId()));
        var address = repository.save(toDomain(request, people));

        return mapper.map(address, AddressDto.class);
    }

    public List<AddressDto> read() {
        var addressList = repository.findAll();

        if (addressList.isEmpty()) {
            throw new DataEmptyOrNullException(ADDRESS_NOT_FOUND);
        }

        return addressList.stream().map(address -> mapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
    }

    public AddressDto update(Long id, AddressRequest request) {
        validator.validateRequest(request);

        var address = repository.findById(id);

        if (address.isEmpty()) {
            throw new DataEmptyOrNullException(ADDRESS_NOT_FOUND);
        }

        var people = peopleExists(Long.valueOf(request.getPeopleId()));
        var addressChange = updateAddress(address.get(), request, people);

        return mapper.map(addressChange, AddressDto.class);
    }

    public void delete(Long id) {
        var address = repository.findById(id);
        if (address.isEmpty()) {
            throw new DataEmptyOrNullException(ADDRESS_NOT_FOUND);
        }

        repository.delete(address.get());
    }

    private void addressExists(AddressRequest request) {
        var address = repository.findByStreetAndDistrictAndCity(request.getStreet(), request.getDistrict(),
                request.getCity());
        if (!isNullOrEmpty(address)) {
            throw new DataIntegratyViolationException(ADDRESS_ALREADY_EXISTS);
        }
    }

    private Address toDomain(AddressRequest request, People people) {
        return Address.builder()
                .street(request.getStreet())
                .number(Integer.parseInt(request.getNumber()))
                .district(request.getDistrict())
                .city(request.getCity())
                .state(request.getState())
                .people(People.builder()
                        .id(people.getId())
                        .name(people.getName())
                        .birthday(people.getBirthday())
                        .gender(people.getGender())
                        .parentage(people.getParentage())
                        .build())
                .build();
    }

    private Address updateAddress(Address address, AddressRequest request, People people) {
        address.setStreet(request.getStreet());
        address.setNumber(Integer.parseInt(request.getNumber()));
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPeople(people);
        repository.saveAndFlush(address);

        return address;
    }

    private People peopleExists(Long id) {
        var people = peopleRepository.findById(id);
        if (people.isEmpty()) {
            throw new DataEmptyOrNullException(PEOPLE_NOT_FOUND);
        }
        return people.get();
    }
}
