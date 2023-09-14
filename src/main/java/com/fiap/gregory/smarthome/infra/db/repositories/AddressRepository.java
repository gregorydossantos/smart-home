package com.fiap.gregory.smarthome.infra.db.repositories;

import com.fiap.gregory.smarthome.infra.db.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByStreetAndDistrictAndCity(String street, String district, String city);
}
