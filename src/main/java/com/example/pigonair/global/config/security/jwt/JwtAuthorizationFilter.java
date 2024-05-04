package com.example.pigonair.global.config.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.pigonair.global.config.security.UserDetailsServiceImpl;
import com.example.pigonair.global.config.security.refreshtoken.TokenService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl adminDetailsService;
	private final TokenService tokenService;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl adminDetailsService, TokenService tokenService) {
		this.jwtUtil = jwtUtil;
		this.adminDetailsService = adminDetailsService;
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws
		ServletException,
		IOException {
		String accessToken = jwtUtil.getTokenFromRequest(req);

		if (StringUtils.hasText(accessToken)) {
			accessToken = jwtUtil.substringToken(accessToken);
			String email = tokenService.getRefreshTokenInfo(accessToken).get("email");
			jwtUtil.validateToken(accessToken, email);
			Claims info = jwtUtil.getUserInfoFromToken(accessToken);
			setAuthentication(info.getSubject());
		}

		filterChain.doFilter(req, res);
	}

	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetails userDetails = adminDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}