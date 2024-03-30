package com.example.pigonair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
		// ConfigurableApplicationContext context =SpringApplication.run(PigonAirApplication.class, args);
		// FlightDataGenerator flightDataGenerator = context.getBean(FlightDataGenerator.class);
		//
		//// Generate random flight data for 100 flights
		// int numberOfFlights = 100;
		// flightDataGenerator.generateRandomFlightData(numberOfFlights);
		//
		//// Close the application context
		// context.close();
	}
	//
	// @Bean
	// public CommandLineRunner generateDummyData(SeatRepository seatRepository, FlightRepository flightRepository) {
	// 	return (args) -> {
	// 		Random random = new Random();
	// 		Flight flight = flightRepository.findById(21L).orElseThrow();
	// 		// 80개의 더미 데이터 생성
	// 		for (int i = 0; i < 80; i++) {
	// 			// 가격을 10000부터 50000 사이의 랜덤값으로 생성
	// 			long price = 10000 + random.nextInt(40000);
	// 			// 등급을 1부터 3 사이의 랜덤값으로 생성
	// 			int grade = random.nextInt(3) + 1;
	// 			Seat seat = Seat.builder()
	// 				.price(price)
	// 				.grade(grade)
	// 				.isAvailable(true)
	// 				.flight(flight)
	// 				.build();
	// 			seatRepository.save(seat);
	// 		}
	// 	};
	// }
}