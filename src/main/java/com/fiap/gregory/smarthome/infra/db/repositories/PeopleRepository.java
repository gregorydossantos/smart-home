package com.fiap.gregory.smarthome.infra.db.repositories;

import com.fiap.gregory.smarthome.infra.db.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {

    Optional<People> findByNameAndGenderAndParentage(String name, String gender, String parentage);
}
