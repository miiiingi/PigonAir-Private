package com.example.pigonair.member;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.member.repository.MemberRepository;
import com.example.pigonair.domain.member.service.MemberServiceImpl;
import com.example.pigonair.global.config.common.exception.CustomException;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class MemberTests {
	@Autowired
	private MemberServiceImpl memberService;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입 실패 테스트")
	void signUpFailureTest() {
		// given
		Member test = Member.builder()
			.email("failure@test.com")
			.password("1234")
			.name("test")
			.phoneNumber("010-0000-0000")
			.build();

		// when & then
		memberRepository.save(test);
		MemberRequestDto.SignupRequestDto requestDto = new MemberRequestDto.SignupRequestDto(
			"failure@test.com", "1234", "test", "010-0000-0000"
		);
		assertThrows(CustomException.class, () -> {
			memberService.signUp(requestDto);
		});

	}

	@Test
	@DisplayName("회원가입 성공 테스트")
	void signUpSuccessTest() {
		// given
		String email = "success@test.com";
		String password = "1234";
		String name = "test";
		String phoneNumber = "010-0000-0000";
		MemberRequestDto.SignupRequestDto requestDto = new MemberRequestDto.SignupRequestDto(
			email, password, name, phoneNumber
		);
		// when
		memberService.signUp(requestDto);

		// then
		Optional<Member> result = memberRepository.findByEmail(email);
		assertTrue(result.isPresent(), "회원가입 후, 사용자는 데이터베이스에 존재해야 합니다.");
		result.ifPresent(member -> {
			assertEquals(email, member.getEmail(), "이메일이 일치해야 합니다.");
		});

	}

	// @Test
	// @DisplayName("로그인 성공 테스트")
	// void loginTest() {
	// 	// given
	// 	String email = "loginSuccess@test.com";
	// 	String password = "1234";
	// 	Member member = Member.builder()
	// 		.email(email)
	// 		.password(passwordEncoder.encode(password)) // 비밀번호 암호화하여 저장
	// 		.name("Test User")
	// 		.phoneNumber("010-1234-5678")
	// 		.build();
	// 	memberRepository.save(member);
	// 	MemberRequestDto.LoginRequestDto requestDto = new MemberRequestDto.LoginRequestDto(
	// 		email, password
	// 	);
	//
	// 	// when
	// 	Authentication authentication = authenticationManager.authenticate(
	// 		new UsernamePasswordAuthenticationToken(
	// 			requestDto.email(),
	// 			requestDto.password()
	// 		)
	// 	);
	//
	// 	// then
	// 	assertTrue(authentication.isAuthenticated(), "인증이 성공적으로 완료되어야 합니다.");
	//
	// }
	@Test
	@DisplayName("로그인 실패 테스트")
	void loginFailureTest() {
		// given
		String wrongEmail = "nonexist@test.com";
		String wrongPassword = "wrongPassword";
		MemberRequestDto.LoginRequestDto requestDto = new MemberRequestDto.LoginRequestDto(
			wrongEmail, wrongPassword
		);

		// when & then
		assertThrows(AuthenticationException.class, () -> {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDto.email(),
					requestDto.password()
				)
			);
		}, "잘못된 자격 증명으로는 로그인에 실패해야 합니다.");
	}

}
