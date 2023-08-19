package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.AddressRegisterDto;
import com.fiap.gregory.smarthome.app.request.AddressRegisterRequest;
import com.fiap.gregory.smarthome.app.services.AddressRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/address-register")
public class AddressRegisterController {

    private static final String PATH_ID = "/{id}";

    @Autowired
    private AddressRegisterService service;

    @PostMapping
    public ResponseEntity<AddressRegisterDto> create(@RequestBody AddressRegisterRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<AddressRegisterDto>> readAll() {
        return ResponseEntity.ok(service.read());
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<AddressRegisterDto> update(@PathVariable("id") Long id,
                                                     @RequestBody AddressRegisterRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(PATH_ID)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
