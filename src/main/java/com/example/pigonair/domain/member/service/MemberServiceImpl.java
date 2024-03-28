package com.example.pigonair.domain.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;
import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

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
}