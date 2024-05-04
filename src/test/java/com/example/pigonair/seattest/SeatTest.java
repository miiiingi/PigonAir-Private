// package com.example.pigonair.seattest;
//
// import java.util.ArrayList;
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
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.PersistenceContext;
//
// @Transactional
// @SpringBootTest
// public class SeatTest {
//
// 	@PersistenceContext
// 	private EntityManager entityManager;
//
// 	@Autowired
// 	private SeatRepository seatRepository;
//
// 	@Autowired
// 	private FlightRepository flightRepository;
//
// 	@Test
// 	@Rollback(value = false)
// 	public void inputSeat() throws Exception {
// 		List<Flight> flights = flightRepository.findAllByIdBetween(40001L, 50000L);
// 		Random random = new Random();
// 		List<Seat> seats = new ArrayList<>();
//
// 		for (Flight flight : flights) {
// 			for (int i = 1; i <= 80; i++) {
// 				int grade = random.nextInt(3) + 1; // Generates random grade between 1 and 3
// 				long price = (grade + 1) * 1500L; // Generates random price between 1000 and 6000
// 				Seat seat = Seat.builder()
// 					.flight(flight)
// 					.grade(grade)
// 					.price(price)
// 					.isAvailable(true)
// 					.build();
// 				seats.add(seat);
//
// 				if (seats.size() % 20 == 0) {
// 					saveSeats(seats); // Save seats in batches
// 					seats.clear(); // Clear seats list
// 				}
// 			}
// 		}
//
// 		// Save any remaining seats
// 		if (!seats.isEmpty()) {
// 			saveSeats(seats);
// 		}
// 	}
//
// 	private void saveSeats(List<Seat> seats) {
// 		seatRepository.saveAll(seats);
// 		entityManager.flush(); // Flush to database
// 		entityManager.clear(); // Clear the persistence context to release memory
// 	}
// }