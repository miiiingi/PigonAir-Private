package com.example.pigonair.domain.reservation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.member.entity.Member;
import org.springframework.dao.DataAccessException;
import com.example.pigonair.domain.member.repository.MemberRepository;
import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.domain.seat.entity.Seat;
import com.example.pigonair.domain.seat.repository.SeatRepository;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;
import com.example.pigonair.global.config.security.UserDetailsImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

	private final ReservationRepository reservationRepository;
	private final FlightRepository flightRepository;
	private final MemberRepository memberRepository;
	private final SeatRepository seatRepository;
	private final EntityManager entityManager;
	private AtomicBoolean lock = new AtomicBoolean(false);

	@Override
	@Transactional
	public void saveReservation(ReservationRequestDto requestDto, UserDetailsImpl userDetails) {

		Member member = userDetails.getUser();

		Seat seat = getSeat(requestDto);      // 좌석 정보 확인 및 가져오기

		Flight flight = getFlight(seat);    // 비행기 가져오기

		// entityManager.lock(seat, LockModeType.PESSIMISTIC_READ);
		entityManager.lock(seat, LockModeType.PESSIMISTIC_WRITE);
		checkIsAvailableSeat(seat); // 예약 가능한 좌석인지 확인
		seat.seatPick();    // 좌석 예매 불가로 변경
		makeAndSaveReservation(member, seat, flight);

	}

	@Transactional
	public void updateReservationStatus() {
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime fifteenMinutesAgo = currentTime.minusMinutes(15);

		List<Reservation> expiredReservations = reservationRepository.findByIsPaymentFalseAndReservationDateBefore(
			fifteenMinutesAgo);

		returnSeatIsAvailable(expiredReservations);
	}

	@Override
	@Transactional
	public void cancelReservation(Long reservation_id, UserDetailsImpl userDetails) {
		Reservation reservation = getReservation(reservation_id);
		Member member = userDetails.getUser();
		deleteReservation(reservation, member);
	}

	@Override
	public List<ReservationResponseDto> getReservations(UserDetailsImpl userDetails) {
		// 로그인 정보 확인 및 가져오기
		// Member member = getMember(userDetails);
		Member member = userDetails.getUser();
		// 해당 사용자의 예약 중 결제되지 않은 예약 가져오기
		List<Object[]> reservationInfo = reservationRepository.findReservationInfoByMemberAndIsPaymentWithFlightAndSeat(
			member, false);
		// responseDto 만들기
		List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();
		for (Object[] row : reservationInfo) {
			Long id = (Long)row[0];
			LocalDateTime departureDate = (LocalDateTime)row[1];
			Airport origin = (Airport)row[2];
			Airport destination = (Airport)row[3];
			int seatNumber = (int)row[4];
			Long price = (Long)row[5];

			reservationResponseDtos.add(
				new ReservationResponseDto(id, departureDate, origin.name(), destination.name(), seatNumber, price));
		}

		return reservationResponseDtos;
	}

	// --------------------------private method--------------------------

	private Member getMember(UserDetailsImpl userDetails) {
		Member member = memberRepository.findById(userDetails.getUser().getId()).orElseThrow(() ->
			new CustomException(ErrorCode.NOT_FOUND_MEMBER));
		return member;
	}

	private Seat getSeat(ReservationRequestDto requestDto) {
		Seat seat = seatRepository.findById(requestDto.seatId()).orElseThrow(() ->
			new CustomException(ErrorCode.NOT_FOUND_SEAT));
		return seat;
	}

	private Flight getFlight(Seat seat) {
		Flight flight = flightRepository.findById(seat.getFlight().getId()).orElseThrow(() ->
			new CustomException(ErrorCode.NOT_FOUND_FLIGHT));
		return flight;
	}

	private void checkIsAvailableSeat(Seat seat) {
		if (!seat.isAvailable()) {
			throw new CustomException(ErrorCode.ALREADY_RESERVED_SEAT);
		}
	}

	private static Reservation makeReservation(Member member, Seat seat, Flight flight) {
		Reservation reservation = Reservation.builder()
			.member(member)
			.seat(seat)
			.flight(flight)
			.reservationDate(LocalDateTime.now())
			.isPayment(false)
			.build();
		return reservation;
	}

	private void makeAndSaveReservation(Member member, Seat seat, Flight flight) {
		Reservation reservation = makeReservation(member, seat, flight);    // 예약 만들기
		try {
			reservationRepository.save(reservation);
		} catch (DataAccessException e) {
			throw new CustomException(ErrorCode.ALREADY_RESERVED_SEAT);
		}
	}

	private void returnSeatIsAvailable(List<Reservation> expiredReservations) {
		for (Reservation reservation : expiredReservations) {
			// isPayment 업데이트
			reservationRepository.delete(reservation);

			// Seat의 isAvailable 업데이트
			Seat seat = reservation.getSeat();
			seat.setIsAvailable();
			seatRepository.save(seat);
		}
	}

	private void deleteReservation(Reservation reservation, Member member) {
		if (reservation.getMember().getId().equals(member.getId())) {
			reservation.getSeat().setIsAvailable();
			reservationRepository.delete(reservation);
		} else
			throw new CustomException(ErrorCode.FORBIDDEN);
	}

	private Reservation getReservation(Long reservation_id) {
		Reservation reservation = reservationRepository.findById(reservation_id).orElseThrow(() ->
			new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
		return reservation;
	}

}
