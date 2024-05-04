package com.example.pigonair.payment;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.member.repository.MemberRepository;
import com.example.pigonair.domain.member.service.MemberServiceImpl;
import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.repository.PaymentRepository;
import com.example.pigonair.domain.payment.service.PaymentServiceImpl;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.domain.seat.entity.Seat;
import com.example.pigonair.domain.seat.repository.SeatRepository;
import com.example.pigonair.global.config.common.exception.CustomException;

@SpringBootTest
@Transactional
public class PaymentServiceTest {

	@Autowired
	private PaymentServiceImpl paymentService;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberServiceImpl memberService;

	private Member saveTestMember() {
		Member member = Member.builder()
			.email("test@email.com")
			.name("test")
			.phoneNumber("00000000000")
			.password("test1234")
			.build();
		return memberRepository.save(member);
	}

	private Flight saveTestFlight() {
		Flight flight = Flight.builder()
			.departureTime(LocalDateTime.now().plusDays(1))
			.arrivalTime(LocalDateTime.now().plusDays(1).plusHours(2))
			.origin(Airport.CDG)
			.destination(Airport.JFK)
			.build();
		return flightRepository.save(flight);
	}

	private Seat saveTestSeat(Flight flight) {
		Seat seat = Seat.builder()
			.flight(flight)
			.price(200L)
			.grade(1)
			.isAvailable(true)
			.build();
		return seatRepository.save(seat);
	}

	private Reservation createAndSaveTestReservation(Flight flight, Member member, Seat seat) {
		Reservation reservation = Reservation.builder()
			.member(member)
			.seat(seat)
			.flight(flight)
			.reservationDate(LocalDateTime.now())
			.isPayment(false)
			.build();

		return reservationRepository.save(reservation);
	}

	@Test
	void payProcessTest() {
		//Given
		Flight flight = saveTestFlight();
		Member member = saveTestMember();
		Seat seat = saveTestSeat(flight);
		Reservation reservation = createAndSaveTestReservation(flight, member, seat);

		// When
		var response = paymentService.payProcess(reservation.getId(), member);

		//Then
		assertNotNull(response);
		assertNotNull(response.payUuid());
		assertEquals(member.getName(), response.memberName());
	}

	@Test
	void postPayProcess_Successful() {
		// Given
		Flight flight = saveTestFlight();
		Member member = saveTestMember();
		Seat seat = saveTestSeat(flight);
		Reservation reservation = createAndSaveTestReservation(flight, member, seat);

		PostPayRequestDto requestDto = new PostPayRequestDto(reservation.getId(), seat.getPrice(), "serialNumber123",member.getEmail());

		// When
		paymentService.postPayProcess(requestDto);

		// Then
		assertTrue(reservation.isPayment());
	}

	@Test
	void postPayProcess_PaymentAmountMismatch() {
		// Given
		Flight flight = saveTestFlight();
		Member member = saveTestMember();
		Seat seat = saveTestSeat(flight);
		Reservation reservation = createAndSaveTestReservation(flight, member, seat);

		PostPayRequestDto requestDto = new PostPayRequestDto(reservation.getId(), seat.getPrice() + 100,
			"serialNumber123",member.getEmail());

		//When //Then
		assertThrows(CustomException.class, () -> paymentService.postPayProcess(requestDto));
		assertFalse(reservation.isPayment());
	}

}
