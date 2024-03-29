package com.example.pigonair.domain.member.service;

import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.TicketResponseDto;

public interface MemberService {
	void signUp(MemberRequestDto.SignupRequestDto requestDto);

	TicketResponseDto getTicketPage(Member member);
}
