package com.example.pigonair.domain.reservation.entity;

import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.seat.entity.Seat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne // 추후에 @ManyToOne
	private Member member;

	@ManyToOne
	private Seat seat;

	@ManyToOne
	private Flight flight;

	private LocalDateTime reservationDate;
	private boolean isPayment;

	@Builder
	public Reservation(Member member, Seat seat, Flight flight, LocalDateTime reservationDate, boolean isPayment) {
		this.member = member;
		this.seat = seat;
		this.flight = flight;
		this.reservationDate = reservationDate;
		this.isPayment = isPayment;
	}
}