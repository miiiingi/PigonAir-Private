package com.example.pigonair.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.pigonair.domain.member.service.MemberServiceImpl;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.domain.member.dto.MemberRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j(topic = "회원가입, 로그인")
@Tag(name = "User API", description = "회원가입, 로그인")
@RequiredArgsConstructor
public class MemberController {
	private final MemberServiceImpl memberServiceImpl;

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("ErrorMessage", null);
		return "signup";
	}

	@GetMapping("/login-page")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/home")
	public String homePage() {
		return "home";
	}

	@GetMapping("/mypage")
	public String myPage(@AuthenticationPrincipal UserDetails userDetails) {
		return "mypage";
	}

	@GetMapping("/ticket")
	public String myTicket(@AuthenticationPrincipal UserDetails userDetails) {
		return "ticket";
	}

	@Operation(summary = "회원가입", description = "회원 가입시 필요한 정보를 입력합니다.")
	@PostMapping("/signup")
	public String signup(MemberRequestDto.SignupRequestDto requestDto, Model model) {
		try {
			memberServiceImpl.signUp(requestDto);
		} catch (CustomException ex) {
			model.addAttribute("ErrorMessage", ex.getErrorCode().getMessage());
			return "signup";
		}
		return "redirect:/login-page";
	}

	@Operation(summary = "로그인", description = "로그인 시 필요한 정보를 입력합니다.")
	@PostMapping("/login")
	public void login(@RequestBody MemberRequestDto.LoginRequestDto requestDto) {

	}

	@GetMapping("/checkLogin")
	public ResponseEntity<?> checkLogin(@AuthenticationPrincipal UserDetails userDetails) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.isAuthenticated()) {
			// 로그인된 상태이면 true를 반환
			return ResponseEntity.ok().body("{\"loggedIn\": true}");
		} else {
			// 로그인되지 않은 상태이면 false를 반환
			return ResponseEntity.ok().body("{\"loggedIn\": false}");
		}
	}

}
