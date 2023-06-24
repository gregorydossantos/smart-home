package com.fiap.gregory.smarthome.app.models.domains;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_home_appliance_manager")
public class HomeApplianceManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String model;
    @Column(unique = true)
    private String brand;
    private Integer voltage;
}
