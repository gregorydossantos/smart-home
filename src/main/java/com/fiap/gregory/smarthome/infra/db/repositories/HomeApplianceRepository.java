package com.fiap.gregory.smarthome.infra.db.repositories;

import com.fiap.gregory.smarthome.infra.db.models.HomeAppliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeApplianceRepository extends JpaRepository<HomeAppliance, Long> {

    Optional<HomeAppliance> findByNameAndModelAndBrand(String name, String model, String brand);
}
