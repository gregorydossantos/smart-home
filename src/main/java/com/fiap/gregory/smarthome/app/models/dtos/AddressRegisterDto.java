package com.fiap.gregory.smarthome.app.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRegisterDto {

    @JsonProperty(access = WRITE_ONLY)
    private Long id;
    private String street;
    private Integer number;
    private String district;
    private String city;
    private String state;
}
