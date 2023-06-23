package com.fiap.gregory.smarthome.app.repositories;

import com.fiap.gregory.smarthome.app.models.domains.AddressRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRegisterRepository extends JpaRepository<AddressRegister, Long> {

    AddressRegister findByStreetAndNumber(String street, Integer number);
}
