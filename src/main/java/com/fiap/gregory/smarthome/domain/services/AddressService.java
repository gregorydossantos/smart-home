package com.fiap.gregory.smarthome.domain.services;

import com.fiap.gregory.smarthome.app.request.AddressRequest;
import com.fiap.gregory.smarthome.domain.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.domain.usecases.AddressUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {

    final AddressUseCase useCase;

    public AddressRegisterDto create(AddressRequest request) {
        return useCase.create(request);
    }

    public List<AddressRegisterDto> read() {
        return useCase.read();
    }

    public AddressRegisterDto update(Long id, AddressRequest request) {
        return useCase.update(id, request);
    }

    public void delete(Long id) {
        useCase.delete(id);
    }
}
