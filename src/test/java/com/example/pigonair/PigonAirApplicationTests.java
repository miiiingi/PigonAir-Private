package com.example.pigonair;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.example.pigonair.domain.member.repository.MemberRepository;
import com.example.pigonair.domain.member.service.MemberServiceImpl;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class PigonAirApplicationTests {
	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Test
	void signUpTest() {
		// given
		MemberRequestDto.SignupRequestDto requestDto = new MemberRequestDto.SignupRequestDto(
			"test96@test.com", "1234", "test", "010-0000-0000"
		);
		if (memberRepository.findByEmail(requestDto.email()).isPresent()) {
			assertThrows(CustomException.class, () -> {
				memberService.signUp(requestDto);
			});

		} else {
			assertDoesNotThrow(() -> {
				memberService.signUp(requestDto);
			});
		}

	}

	@Test
	void loginTest() {
		// given
		MemberRequestDto.LoginRequestDto requestDto = new MemberRequestDto.LoginRequestDto(
			"test01@test.com", "1234"
		);

		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				requestDto.email(),
				requestDto.password(),
				null
			)
		);
		if (authentication.isAuthenticated()) {
			// 로그인 성공
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			// 로그인 실패 - CustomException 발생
			throw new CustomException(ErrorCode.INVALID_EMAIL_OR_PASSWORD);
		}

	}

}
