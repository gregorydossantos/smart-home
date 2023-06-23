package com.fiap.gregory.smarthome.app.models.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_address_register")
public class AddressRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String street;
    @Column(unique = true)
    private Integer number;
    private String district;
    private String city;
    @Size(max = 2)
    private String state;
}
