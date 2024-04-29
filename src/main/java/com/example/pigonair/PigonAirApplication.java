package com.example.pigonair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class PigonAirApplication {
	public static void main(String[] args) {
		SpringApplication.run(PigonAirApplication.class, args);
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