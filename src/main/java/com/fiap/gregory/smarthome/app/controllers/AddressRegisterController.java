package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.AddressRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/address-register")
public class AddressRegisterController {

    @Autowired
    private AddressRegisterService service;

    @PostMapping
    public ResponseEntity<AddressRegisterDto> create(@RequestBody AddressRegisterRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }
}
