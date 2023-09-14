package com.fiap.gregory.smarthome.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeopleManagementDto {

    @JsonProperty(access = WRITE_ONLY)
    private Long id;
    private String name;
    private Date birthday;
    private String gender;
    private String parentage;
}
