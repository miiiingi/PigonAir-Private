package com.example.pigonair.payment.service;

import com.example.pigonair.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.reservation.entity.Reservation;

public interface PaymentService {
	//결제 이후 예약의 isPayment 필드를 변경
	TicketResponseDto postPayProcess(PostPayRequestDto requestDto);

	void savePayInfo(String serialNumber, Reservation reservation);
}
