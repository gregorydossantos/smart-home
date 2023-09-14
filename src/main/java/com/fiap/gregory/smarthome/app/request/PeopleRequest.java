package com.fiap.gregory.smarthome.app.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PeopleRequest {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String birthday;

    @NotNull
    @NotEmpty
    @Size(max = 1)
    private String gender;

    @NotNull
    @NotEmpty
    private String parentage;
}
