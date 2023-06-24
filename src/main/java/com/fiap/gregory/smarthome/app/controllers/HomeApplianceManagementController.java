package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
import com.fiap.gregory.smarthome.app.services.HomeApplianceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/home-appliance")
public class HomeApplianceManagementController {

    @Autowired
    private HomeApplianceManagementService service;

    @PostMapping
    public ResponseEntity<HomeApplianceManagementDto> create(@RequestBody HomeApplianceManagementRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }
}
