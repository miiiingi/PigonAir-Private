package com.example.pigonair.domain.payment.dto;

public class EmailDto {
	public record EmailSendDto(
		Long paymentId,    // 예약 Id
		String email
	) {
	}
}
