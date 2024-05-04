package com.example.pigonair.global.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.pigonair.global.config.jmeter.JmeterService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {
	private final JmeterService jmeterService;

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("ErrorMessage", null);
		return "signup";
	}

	@GetMapping("/login-page")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/")
	public String homePage(HttpServletRequest request) {
		jmeterService.setTransactionNameBasedOnJMeterTag(request);
		return "index";
	}

	@GetMapping("/mypage")
	public String myPage(@AuthenticationPrincipal UserDetails userDetails) {
		return "mypage";
	}
	@GetMapping("/error-page")
	public String errorPage() {
		return "error";
	}

	@GetMapping("/favicon.ico")
	@ResponseBody
	public void returnNoFavicon() {
	}

}
