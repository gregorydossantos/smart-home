package com.fiap.gregory.smarthome.app.repositories;

import com.fiap.gregory.smarthome.app.models.domains.HomeApplianceManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeApplianceManagementRepository extends JpaRepository<HomeApplianceManagement, Long> {

    Optional<HomeApplianceManagement> findByNameAndModelAndBrand(String name, String model, String brand);
}
