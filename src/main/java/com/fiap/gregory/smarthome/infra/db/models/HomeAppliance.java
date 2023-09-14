package com.fiap.gregory.smarthome.infra.db.models;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_home_appliance")
public class HomeAppliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String model;
    private String brand;
    private Integer voltage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "people_id")
    private People people;
}
