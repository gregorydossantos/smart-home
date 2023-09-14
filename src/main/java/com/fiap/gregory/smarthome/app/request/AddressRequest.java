package com.fiap.gregory.smarthome.app.request;

import com.fiap.gregory.smarthome.domain.annotations.ValidateInteger;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    @ValidateInteger(message = "Valor invalido")
    private String number;

    @NotNull
    @NotEmpty
    private String district;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    @Size(max = 2)
    private String state;

    @NotNull
    @NotEmpty
    @ValidateInteger(message = "Valor invalido")
    private String peopleId;
}
