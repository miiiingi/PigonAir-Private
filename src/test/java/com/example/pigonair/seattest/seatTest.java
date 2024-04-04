// package com.example.pigonair.seattest;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.time.LocalDateTime;
// import java.util.List;
//
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Rollback;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.pigonair.domain.flight.entity.Airport;
// import com.example.pigonair.domain.flight.entity.Flight;
// import com.example.pigonair.domain.flight.repository.FlightRepository;
// import com.example.pigonair.domain.seat.entity.Seat;
// import com.example.pigonair.domain.seat.repository.SeatRepository;
//
//
//
//
// @Transactional
// @SpringBootTest
// public class seatTest {
// 	@Autowired
// 	private SeatRepository seatRepository;
// 	@Autowired
// 	private FlightRepository flightRepository;
//
// 	@Test
// 	@Disabled
// 	@Rollback(value = false)
// 	public void inputSeat() throws Exception{
// 		Flight flight = flightRepository.findById(1L).orElseThrow(() ->
// 			new NullPointerException("해당 비행기는 존재하지 않습니다."));
// 		for(int i=1; i<81 ;i++){
// 			Seat seat = Seat.builder()
// 						.flight(flight)
// 						.grade(2)
// 						.price(2000L)
// 						.isAvailable(true).build();
// 			seatRepository.save(seat);
// 		}
//
// 		// assertThat(seatRepository.findAll().size()).isEqualTo(80);
//
// 	}
//
// 	@Test
// 	public void findSeat() throws Exception{
// 	    //given
// 		Flight flight2 = Flight.builder()
// 			.arrivalTime(LocalDateTime.now())
// 			.departureTime(LocalDateTime.now())
// 			.origin(Airport.JFK)
// 			.destination(Airport.AMS)
// 			.build();
// 		flightRepository.save(flight2);
//
// 		Seat seat = Seat.builder()
// 			.flight(flight2)
// 			.grade(1)
// 			.price(20000L)
// 			.isAvailable(true)
// 			.build();
// 		seatRepository.save(seat);
//
// 	    //when
// 		List<Seat> seat_test = seatRepository.findAllByflightId(1L);
//
// 	    //then
// 		assertThat(seat_test).hasSize(1)
// 			.extracting("grade","price","isAvailable") // 검증하고자 한 필드만 추출할 수 있다.
// 			.containsExactlyInAnyOrder(
// 				tuple(1,20000L,true)
// 			);
// 	}
// }
