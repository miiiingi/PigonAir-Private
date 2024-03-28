package com.example.pigonair.domain.payment.service;

import com.example.pigonair.domain.payment.dto.PaymentRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto;
import com.example.pigonair.reservation.entity.Reservation;

public interface PaymentService {
	//결제 이후 예약의 isPayment 필드를 변경
	PaymentResponseDto.TicketResponseDto postPayProcess(PaymentRequestDto.PostPayRequestDto requestDto);

	void savePayInfo(String serialNumber, Reservation reservation);
}
