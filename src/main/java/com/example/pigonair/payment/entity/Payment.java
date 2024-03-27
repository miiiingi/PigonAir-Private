package com.example.pigonair.payment.entity;

import com.example.pigonair.flight.entity.Flight;
import com.example.pigonair.member.entity.Member;
import com.example.pigonair.reservation.entity.Reservation;
import com.example.pigonair.seat.entity.Seat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Reservation reservation;

	@ManyToOne
	private Member member;

	@ManyToOne
	private Seat seat;

	@ManyToOne
	private Flight flight;

	private String serialNumber;
	private LocalDateTime createdAt;
	private boolean isCancelled;

	@Builder
	public Payment(Reservation reservation, Member member, Seat seat, Flight flight, String serialNumber,
		LocalDateTime createdAt, boolean isCancelled) {
		this.reservation = reservation;
		this.member = member;
		this.seat = seat;
		this.flight = flight;
		this.serialNumber = serialNumber;
		this.createdAt = createdAt;
		this.isCancelled = isCancelled;
	}
}