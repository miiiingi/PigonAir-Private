package com.example.pigonair.domain.payment.dto;

import java.time.LocalDateTime;

import com.example.pigonair.domain.flight.entity.Airport;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.reservation.entity.Reservation;

public class PaymentResponseDto {
	public record TicketResponseDto(
		Long reservationId,
		LocalDateTime departureTime,
		Airport origin,
		Airport destination,
		int seatNumber,
		String name,
		boolean isPayment
	) {
		public TicketResponseDto(Reservation reservation) {
			this(
				reservation.getId(),
				reservation.getFlight().getDepartureTime(),
				reservation.getFlight().getOrigin(),
				reservation.getFlight().getDestination(),
				reservation.getSeat().getNumber(),
				reservation.getMember().getName(),
				reservation.isPayment()
			);
		}
	}

	public record PayResponseDto(
		String payUuid,
		Long reservationId,
		Long price,
		String phoneNumber,
		String memberName,
		String memberEmail,
		String impKey
	) {
		public PayResponseDto(Reservation reservation, Member member, String payUlid, String impKey) {
			this(
				payUlid,
				reservation.getId(),
				reservation.getSeat().getPrice(),
				member.getPhoneNumber(),
				member.getName(),
				member.getEmail(),
				impKey
			);
		}
	}
}
