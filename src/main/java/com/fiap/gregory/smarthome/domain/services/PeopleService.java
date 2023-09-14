package com.fiap.gregory.smarthome.domain.services;

import com.fiap.gregory.smarthome.app.request.PeopleRequest;
import com.fiap.gregory.smarthome.domain.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.domain.usecases.PeopleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PeopleService {

    final PeopleUseCase useCase;

    public PeopleManagementDto create(PeopleRequest request) {
        return useCase.create(request);
    }

    public List<PeopleManagementDto> read() {
        return useCase.read();
    }

    public PeopleManagementDto update(Long id, PeopleRequest request) {
        return useCase.update(id, request);
    }

    public void delete(Long id) {
        useCase.delete(id);
    }
}
