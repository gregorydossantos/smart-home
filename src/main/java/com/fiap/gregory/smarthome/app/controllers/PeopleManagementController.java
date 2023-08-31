package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.PeopleManagementDto;
import com.fiap.gregory.smarthome.app.request.PeopleManagementRequest;
import com.fiap.gregory.smarthome.app.services.PeopleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/people")
public class PeopleManagementController {

    private static final String PATH_ID = "/{id}";

    @Autowired
    private PeopleManagementService service;

    @PostMapping
    public ResponseEntity<PeopleManagementDto> create(@RequestBody PeopleManagementRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<PeopleManagementDto>> readAll() {
        return ResponseEntity.ok(service.read());
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<PeopleManagementDto> update(@PathVariable("id") Long id,
                                                      @RequestBody PeopleManagementRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping(PATH_ID)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
