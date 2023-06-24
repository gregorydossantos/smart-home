package com.fiap.gregory.smarthome.app.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeApplianceManagementRequest {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String model;

    @NotNull
    @NotEmpty
    private String brand;

    @NotNull
    @NotEmpty
    private String voltage;
}
