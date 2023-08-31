package com.fiap.gregory.smarthome.app.repositories;

import com.fiap.gregory.smarthome.app.models.domains.PeopleManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleManagementRepository extends JpaRepository<PeopleManagement, Long> {

    Optional<PeopleManagement> findByNameAndGenderAndParentage(String name, String gender, String parentage);
}
