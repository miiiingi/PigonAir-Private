package com.example.pigonair.domain.reservation.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.pigonair.domain.flight.entity.Airport;
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;
    private final EntityManager entityManager;

	@Override
	@Transactional
	public void saveReservation(ReservationRequestDto requestDto, UserDetailsImpl userDetails) {

		// Member member = getMember(userDetails); // 로그인 정보 확인 및 가져오기
        Member member = userDetails.getUser();

        Seat seat = getSeat(requestDto);      // 좌석 정보 확인 및 가져오기

        Flight flight = getFlight(seat);    // 비행기 가져오기

        checkIsAvailableSeat(seat); // 예약 가능한 좌석인지 확인
        entityManager.lock(seat, LockModeType.PESSIMISTIC_WRITE);

        Reservation reservation = makeReservation(member, seat, flight);    // 예약 만들기
        seat.seatPick();    // 좌석 예매 불가로 변경
        reservationRepository.save(reservation);
    }

    @Transactional
    public void updateReservationStatus() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime fifteenMinutesAgo = currentTime.minusMinutes(15);

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
        Member member = userDetails.getUser();
        if(reservation.getMember().getId().equals(member.getId())){
            reservation.getSeat().setIsAvailable();
            reservationRepository.delete(reservation);
        }
        else
            throw new CustomException(ErrorCode.FORBIDDEN);
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
            Long id = (Long) row[0];
            LocalDateTime departureDate = (LocalDateTime) row[1];
            Airport origin = (Airport)row[2];
            Airport destination = (Airport)row[3];
            Long seatNumber = (Long) row[4];
            Long price = (Long) row[5];

            reservationResponseDtos.add(new ReservationResponseDto(id, departureDate, departureDate, origin.name(), destination.name(), seatNumber, price));
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

    // private static List<ReservationResponseDto> getReservationResponseDtos(List<Reservation> reservations) {
    //     List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();
    //     reservations.stream().forEach(reservation -> {
    //         Long id = reservation.getId();
    //         Flight flight = reservation.getFlight();
    //         LocalDateTime departureDate = flight.getDepartureTime();
    //         LocalDateTime departureTime = flight.getDepartureTime();
    //         String origin = flight.getOrigin().getFullName();
    //         String destination = flight.getDestination().getFullName();
    //
    //         Seat seat = reservation.getSeat();
    //         Long seatNumber = seat.getId();
    //         Long price = seat.getPrice();
    //         reservationResponseDtos.add(new ReservationResponseDto(id,departureDate, departureTime,
    //                 origin, destination, seatNumber, price));
    //
    //     });
    //     return reservationResponseDtos;
    // }


    private Reservation getReservation(Long reservation_id) {
        Reservation reservation = reservationRepository.findById(reservation_id).orElseThrow(()->
            new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        return reservation;
    }
}
