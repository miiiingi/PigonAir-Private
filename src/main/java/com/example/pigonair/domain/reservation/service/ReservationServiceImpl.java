package com.example.pigonair.domain.reservation.service;

import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.reservation.dto.ReservationRequestDto;
import com.example.pigonair.domain.reservation.dto.ReservationResponseDto;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.flight.repository.FlightRepository;
import com.example.pigonair.domain.member.repository.MemberRepository;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.domain.seat.repository.SeatRepository;
import com.example.pigonair.domain.seat.entity.Seat;
import com.example.pigonair.global.config.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final MemberRepository memberRepository;
    private final SeatRepository seatRepository;


    @Override
    public void saveReservation(ReservationRequestDto requestDto, UserDetailsImpl userDetails) {
        //
        Member member = memberRepository.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new NullPointerException());
        Flight flight = flightRepository.findById(requestDto.flightId()).orElseThrow(() ->
                new NullPointerException());

        Seat seat = seatRepository.findById(requestDto.seatId()).orElseThrow(() ->
                new NullPointerException());

        if (flight.getId() != seat.getFlight().getId()) {
            throw new IllegalArgumentException();
        }

        if (!seat.isAvailable()) {
            throw new IllegalArgumentException();
        }

        Reservation reservation = Reservation.builder()
                .member(member)
                .seat(seat)
                .flight(flight)
                .reservationDate(LocalDateTime.now())
                .isPayment(false)
                .build();

        seat.seatPick();


        reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationResponseDto> getReservations(UserDetailsImpl userDetails) {
        Member member = memberRepository.findById(userDetails.getUser().getId()).orElseThrow(() ->
                new NullPointerException());
        List<Reservation> reservations = reservationRepository.findByMemberAndIsPayment(member, false);
        List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();
        reservations.stream().forEach(reservation -> {
            Long id = reservation.getId();
            String name = reservation.getMember().getName();
            Flight flight = reservation.getFlight();
            LocalDateTime departureDate = flight.getDepartureTime();
            LocalDateTime departureTime = flight.getDepartureTime();
            String origin = flight.getOrigin().getFullName();
            String destination = flight.getDestination().getFullName();
            Long seatNumber = reservation.getSeat().getId();
            reservationResponseDtos.add(new ReservationResponseDto(id, name, departureDate, departureTime,
                    origin, destination, seatNumber));

        });

        return reservationResponseDtos;
    }
}
