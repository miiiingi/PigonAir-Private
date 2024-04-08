package com.example.pigonair.domain.reservation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.member.entity.Member;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;

	@Override
	@Transactional
	public void saveReservation(ReservationRequestDto requestDto, UserDetailsImpl userDetails) {

		Member member = getMember(userDetails); // 로그인 정보 확인 및 가져오기

        Seat seat = getSeat(requestDto);      // 좌석 정보 확인 및 가져오기

        Flight flight = getFlight(seat);    // 비행기 가져오기

        checkIsAvailableSeat(seat); // 예약 가능한 좌석인지 확인

        Reservation reservation = makeReservation(member, seat, flight);    // 예약 만들기
        seat.seatPick();    // 좌석 예매 불가로 변경
        reservationRepository.save(reservation);
    }

    @Transactional
    public void updateReservationStatus() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime fifteenMinutesAgo = currentTime.minusMinutes(1);

        List<Reservation> expiredReservations = reservationRepository.findByIsPaymentFalseAndReservationDateBefore(fifteenMinutesAgo);

        for (Reservation reservation : expiredReservations) {
            // isPayment 업데이트
            reservationRepository.delete(reservation);

            // Seat의 isAvailable 업데이트
            Seat seat = reservation.getSeat();
            seat.setIsAvailable();
            seatRepository.save(seat);
        }
    }

    @Override
    @Transactional
    public void cancelReservation(Long reservation_id, UserDetailsImpl userDetails) {
        Reservation reservation = getReservation(reservation_id);
        reservation.getSeat().setIsAvailable();
        reservationRepository.delete(reservation);
    }


    @Override
    public List<ReservationResponseDto> getReservations(UserDetailsImpl userDetails) {
        // 로그인 정보 확인 및 가져오기
        Member member = getMember(userDetails);
        // 해당 사용자의 예약 중 결제되지 않은 예약 가져오기
        List<Reservation> reservations = reservationRepository.findByMemberAndIsPayment(member, false);
        // responseDto 만들기
        List<ReservationResponseDto> reservationResponseDtos = getReservationResponseDtos(reservations);

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

    private static void checkIsAvailableSeat(Seat seat) {
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

    private static List<ReservationResponseDto> getReservationResponseDtos(List<Reservation> reservations) {
        List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();
        reservations.stream().forEach(reservation -> {
            Long id = reservation.getId();
            String name = reservation.getMember().getEmail();
            Flight flight = reservation.getFlight();
            LocalDateTime departureDate = flight.getDepartureTime();
            LocalDateTime departureTime = flight.getDepartureTime();
            String origin = flight.getOrigin().getFullName();
            String destination = flight.getDestination().getFullName();
            Long seatNumber = reservation.getSeat().getId();
            Long price = reservation.getSeat().getPrice();
            String phoneNumber = reservation.getMember().getPhoneNumber();
            reservationResponseDtos.add(new ReservationResponseDto(id, name, departureDate, departureTime,
                    origin, destination, seatNumber, price, phoneNumber));

        });
        return reservationResponseDtos;
    }


    private Reservation getReservation(Long reservation_id) {
        Reservation reservation = reservationRepository.findById(reservation_id).orElseThrow(()->
            new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        return reservation;
    }
}
