package com.fiap.gregory.smarthome.domain.services;

import com.fiap.gregory.smarthome.app.request.ApplianceRequest;
import com.fiap.gregory.smarthome.domain.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.domain.usecases.HomeApplianceUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HomeApplianceService {

    final HomeApplianceUseCase useCase;

    public HomeApplianceManagementDto create(ApplianceRequest request) {
        return useCase.create(request);
    }

    public List<HomeApplianceManagementDto> read() {
        return useCase.read();
    }

    public HomeApplianceManagementDto update(Long id, ApplianceRequest request) {
        return useCase.update(id, request);
    }

    public void delete(Long id) {
        useCase.delete(id);
    }
}
