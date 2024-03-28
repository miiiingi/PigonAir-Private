package com.example.pigonair.seattest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.flight.entity.Flight;
import com.example.pigonair.seat.entity.Seat;
import com.example.pigonair.seat.repository.FlightRepository;
import com.example.pigonair.seat.repository.SeatRepository;




@Transactional
@SpringBootTest
public class seatTest {
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private FlightRepository flightRepository;

	@Test
	@Rollback(value = false)
	public void inputSeat() throws Exception{
		Flight flight = flightRepository.findById(1L).orElseThrow(() ->
			new NullPointerException("해당 비행기는 존재하지 않습니다."));
		for(int i=1; i<81 ;i++){
			Seat seat = Seat.builder()
						.flight(flight)
						.grade(2)
						.price(2000L)
						.isAvailable(true).build();
			seatRepository.save(seat);
		}

		// assertThat(seatRepository.findAll().size()).isEqualTo(80);

	}
}
