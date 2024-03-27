package com.example.pigonair.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.member.dto.MemberRequestDto;
import com.example.pigonair.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j(topic = "회원가입, 로그인")
@Tag(name = "User API", description = "회원가입, 로그인")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("ErrorMessage", null);
		return "signup";
	}

	@GetMapping("/login-page")
	public String loginPage() {
		return "login";
	}

	@Operation(summary = "회원가입", description = "회원 가입시 필요한 정보를 입력합니다.")
	@PostMapping("/signup")
	public String signup(MemberRequestDto.SignupRequestDto requestDto, Model model) {
		try {
			memberService.signUp(requestDto);
		} catch (CustomException ex) {
			model.addAttribute("ErrorMessage", ex.getErrorCode().getMessage());
			return "signup";
		}
		return "redirect:/login-page";
	}

}
