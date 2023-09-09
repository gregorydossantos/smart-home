package com.fiap.gregory.smarthome.app.models.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_people_manager")
public class PeopleManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date birthday;

    @Size(max = 1)
    private String gender;

    private String parentage;

    @OneToMany(mappedBy = "peopleManagement", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AddressRegister> addressRegisterList;

    @OneToMany(mappedBy = "peopleManagement", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<HomeApplianceManagement> homeApplianceList;
}
