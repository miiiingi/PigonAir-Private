package com.example.pigonair.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

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