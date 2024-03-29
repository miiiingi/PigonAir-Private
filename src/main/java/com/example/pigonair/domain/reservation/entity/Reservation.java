package com.example.pigonair.domain.reservation.entity;

import java.time.LocalDateTime;

import com.example.pigonair.domain.flight.entity.Flight;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.seat.entity.Seat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	private boolean isPayment = false;

	@Builder
	public Reservation(Member member, Seat seat, Flight flight, LocalDateTime reservationDate, boolean isPayment) {
		this.member = member;
		this.seat = seat;
		this.flight = flight;
		this.reservationDate = reservationDate;
		this.isPayment = isPayment;
	}

	public void updateIsPayment() {
		this.isPayment = true;
	}
}