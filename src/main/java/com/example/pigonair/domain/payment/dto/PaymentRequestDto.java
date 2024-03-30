package com.example.pigonair.domain.payment.dto;

public class PaymentRequestDto {
	public record PostPayRequestDto(
		Long id,    // 예약 Id
		Long paidAmount,    //결제 금액
		String serialNumber    //결제 uuid
	) {

	}
}
