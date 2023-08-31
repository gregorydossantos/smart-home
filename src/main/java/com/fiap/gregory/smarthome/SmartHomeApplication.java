package com.fiap.gregory.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.fiap.gregory.smarthome.app.models.domains")
@EnableJpaRepositories(basePackages = "com.fiap.gregory.smarthome.app.repositories")
@SpringBootApplication
public class SmartHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
	}

}
