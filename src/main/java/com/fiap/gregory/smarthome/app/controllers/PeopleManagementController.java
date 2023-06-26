package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.PeopleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/people")
public class PeopleManagementController {

    @Autowired
    private PeopleManagementService service;

    @PostMapping
    public ResponseEntity<PeopleManagementDto> create(@RequestBody PeopleManagementRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }
}
