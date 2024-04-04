package com.example.pigonair.domain.payment.service;

import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.PayResponseDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.domain.reservation.entity.Reservation;

public interface PaymentService {
	//결제 이후 예약의 isPayment 필드를 변경
	TicketResponseDto postPayProcess(PostPayRequestDto requestDto);

	void savePayInfo(String serialNumber, Reservation reservation);

	PayResponseDto payProcess(Long ReservationId, Member member);
}
