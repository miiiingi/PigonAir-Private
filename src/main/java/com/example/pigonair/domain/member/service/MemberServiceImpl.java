package com.example.pigonair.domain.member.service;

import java.util.List;

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
	public List<TicketResponseDto> getTicketPage(Member member) {
		List<Reservation> reservations = reservationRepository.findAllByMemberId(member.getId());
		return reservations.stream()
			.map(TicketResponseDto::new)
			.toList();
	}
}