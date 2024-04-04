package com.example.pigonair.global.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
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
	public String homePage() {
		return "index";
	}

	@GetMapping("/mypage")
	public String myPage(@AuthenticationPrincipal UserDetails userDetails) {
		return "mypage";
	}
}
