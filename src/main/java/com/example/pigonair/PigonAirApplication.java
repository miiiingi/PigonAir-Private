package com.example.pigonair;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@EnableJpaAuditing
@OpenAPIDefinition(servers = {@Server(url = "https://52.78.141.125.nip.io", description = "hh99finalproject")})
@SpringBootApplication
public class PigonAirApplication {
	public static void main(String[] args) {
		SpringApplication.run(PigonAirApplication.class, args);
//// 항공편 난수 생성 (100개)
//		ConfigurableApplicationContext context =SpringApplication.run(PigonAirApplication.class, args);
//		FlightDataGenerator flightDataGenerator = context.getBean(FlightDataGenerator.class);
//
//// Generate random flight data for 100 flights
//		int numberOfFlights = 100;
//		flightDataGenerator.generateRandomFlightData(numberOfFlights);
//
//// Close the application context
//		context.close();
	}
}
