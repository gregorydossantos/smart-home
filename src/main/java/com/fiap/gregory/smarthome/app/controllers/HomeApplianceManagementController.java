package com.fiap.gregory.smarthome.app.controllers;

import com.fiap.gregory.smarthome.app.models.dtos.HomeApplianceManagementDto;
import com.fiap.gregory.smarthome.app.request.HomeApplianceManagementRequest;
import com.fiap.gregory.smarthome.app.services.HomeApplianceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/home-appliance")
public class HomeApplianceManagementController {

    private static final String PATH_ID = "/{id}";

    @Autowired
    private HomeApplianceManagementService service;

    @PostMapping
    public ResponseEntity<HomeApplianceManagementDto> create(@RequestBody HomeApplianceManagementRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<HomeApplianceManagementDto>> readAll() {
        return ResponseEntity.ok(service.read());
    }

    @PutMapping(PATH_ID)
    public ResponseEntity<HomeApplianceManagementDto> update(@PathVariable("id") Long id,
                                                     @RequestBody HomeApplianceManagementRequest request) {
        var response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(PATH_ID)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
