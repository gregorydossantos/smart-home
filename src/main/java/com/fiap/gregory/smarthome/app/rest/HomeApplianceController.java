package com.fiap.gregory.smarthome.app.rest;

import com.fiap.gregory.smarthome.app.request.ApplianceRequest;
import com.fiap.gregory.smarthome.domain.dtos.HomeApplianceDto;
import com.fiap.gregory.smarthome.domain.services.HomeApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.fiap.gregory.smarthome.domain.useful.CommonsMessages.PATH_ID;

@Tag(name = "Home Appliance Controller")
@RestController
@RequestMapping(value = "/home-appliance", produces = {"application/json"})
@AllArgsConstructor
public class HomeApplianceController {

    final HomeApplianceService service;

    @Operation(summary = "Create a home appliance", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created home appliance"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HomeApplianceDto> create(@RequestBody ApplianceRequest request) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(service.create(request)).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "List of home appliance", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of home appliance"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @GetMapping
    public ResponseEntity<List<HomeApplianceDto>> readAll() {
        return ResponseEntity.ok(service.read());
    }

    @Operation(summary = "Update home appliance", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully update home appliance"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PutMapping(value = PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HomeApplianceDto> update(@PathVariable("id") Long id,
                                                   @RequestBody ApplianceRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Update home appliance", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully delete home appliance"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @DeleteMapping(PATH_ID)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
