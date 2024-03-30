package com.example.pigonair.domain.member.service;

import static com.example.pigonair.global.config.common.exception.ErrorCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.member.repository.MemberRepository;
import com.example.pigonair.domain.payment.dto.PaymentResponseDto.TicketResponseDto;
import com.example.pigonair.domain.reservation.entity.Reservation;
import com.example.pigonair.domain.reservation.repository.ReservationRepository;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final ReservationRepository reservationRepository;

	public void signUp(MemberRequestDto.SignupRequestDto requestDto) {
		if (memberRepository.findByEmail(requestDto.email()).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
		}

		String password = passwordEncoder.encode(requestDto.password());

		Member member = Member.builder()
			.email(requestDto.email())
			.password(password)
			.name(requestDto.name())
			.phoneNumber(requestDto.phoneNumber())
			.build();
		memberRepository.save(member);
	}

	@Override
	public TicketResponseDto getTicketPage(Member member) {
		Reservation reservation = reservationRepository.findByMemberId(member.getId()).orElseThrow(
			() -> new CustomException(RESERVATION_NOT_FOUND)
		);
		return new TicketResponseDto(reservation);
	}
}