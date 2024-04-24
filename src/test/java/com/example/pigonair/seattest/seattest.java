// package com.example.pigonair.seattest;
//
// import java.util.List;
// import java.util.Random;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Rollback;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.pigonair.domain.flight.entity.Flight;
// import com.example.pigonair.domain.flight.repository.FlightRepository;
// import com.example.pigonair.domain.seat.entity.Seat;
// import com.example.pigonair.domain.seat.repository.SeatRepository;
//
// @Transactional
// @SpringBootTest
// public class seattest {
//
// 	@Autowired
// 	private SeatRepository seatRepository;
// 	@Autowired
// 	private FlightRepository flightRepository;
//
// 	@Test
// 	@Rollback(value = false)
// 	public void inputSeat() throws Exception {
// 		Random random = new Random();
// 		for (long flightId = 101; flightId < 500; flightId++) {
// 			Flight flight = flightRepository.findById(flightId).orElse(null);
// 			if (flight != null) {
// 				for (int i = 1; i <= 80; i++) {
// 					int grade = random.nextInt(3) + 1; // Generates random grade between 1 and 3
// 					long price = (grade + 1) * 15000L; // Generates random price between 1000 and 6000
// 					Seat seat = Seat.builder()
// 						.flight(flight)
// 						.grade(grade)
// 						.price(price)
// 						.isAvailable(true)
// 						.build();
// 					seatRepository.save(seat);
// 				}
// 			}
// 		}
// 	}
// }