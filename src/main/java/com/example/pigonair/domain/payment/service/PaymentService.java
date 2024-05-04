package com.example.pigonair.domain.payment.service;

import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.payment.dto.PaymentRequestDto.PostPayRequestDto;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.PayResponseDto;

public interface PaymentService {
	//결제 이후 예약의 isPayment 필드를 변경
	void postPayProcess(PostPayRequestDto requestDto);

	Long savePayInfo(PostPayRequestDto postPayRequestDto);

	PayResponseDto payProcess(Long ReservationId, Member member);
}
