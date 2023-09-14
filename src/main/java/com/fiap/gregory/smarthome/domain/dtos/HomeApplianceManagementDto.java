package com.fiap.gregory.smarthome.domain.dtos;

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
public class HomeApplianceManagementDto {

    @JsonProperty(access = WRITE_ONLY)
    private Long id;
    private String name;
    private String model;
    private String brand;
    private Integer voltage;
    private PeopleManagementDto peopleManagement;
}
