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

    private String name;
    private String model;
    private String brand;
    private Integer voltage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "people_id")
    private PeopleManagement peopleManagement;
}
