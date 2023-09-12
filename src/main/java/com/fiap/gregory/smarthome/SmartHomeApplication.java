package com.fiap.gregory.smarthome;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@OpenAPIDefinition(info = @Info(title = "Smart-Home", version = "1.0",
		description = "This is an API that will allow you to manage people and appliance from your home"))
@EntityScan(basePackages = "com.fiap.gregory.smarthome.app.models.domains")
@EnableJpaRepositories(basePackages = "com.fiap.gregory.smarthome.app.repositories")
@SpringBootApplication
public class SmartHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
	}

}
