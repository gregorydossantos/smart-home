package com.fiap.gregory.smarthome.app.models.dtos;

import com.fiap.gregory.smarthome.app.models.domains.State;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRegisterDto {

    private Long id;
    private String street;
    private Integer number;
    private String district;
    private String city;
    private State state;
}
