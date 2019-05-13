package com.proyectDAO.emergencyCare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration("/applicationContext.xml")
public class EmergencyCareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmergencyCareApplication.class, args);
	}

}
