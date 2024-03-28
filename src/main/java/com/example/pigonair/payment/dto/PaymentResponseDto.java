package com.example.pigonair.payment.dto;

import com.example.pigonair.reservation.entity.Reservation;

public class PaymentResponseDto {
	public record TicketResponseDto(
		Reservation reservation
	) {
		public TicketResponseDto(Reservation reservation) {
			this.reservation = reservation;
		}
	}
}
