package com.fiap.gregory.smarthome.app.repositories;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleManagementRepository extends JpaRepository<PeopleManagement, Long> {

    PeopleManagement findByNameAndGenderAndParentage(String name, String gender, String parentage);
}
