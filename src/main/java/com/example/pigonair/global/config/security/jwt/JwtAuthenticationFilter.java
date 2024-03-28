package com.example.pigonair.global.config.security.jwt;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;
import com.example.pigonair.global.config.security.UserDetailsImpl;
import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		log.info("로그인 시도");
		try {
			MemberRequestDto.LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
				MemberRequestDto.LoginRequestDto.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					requestDto.email(),
					requestDto.password(),
					null
				)
			);
		} catch (IOException e) {
			log.info(e.getMessage());
			throw new CustomException(ErrorCode.INVALID_EMAIL_OR_PASSWORD);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.info("로그인 성공 및 JWT 생성");
		String email = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
		String token = jwtUtil.createToken(email);
		jwtUtil.addJwtToCookie(token, response);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		log.info("로그인 실패");
		response.setCharacterEncoding("UTF-8");
		String responseDto = new ObjectMapper().writeValueAsString(
			Map.of("message", ErrorCode.INVALID_EMAIL_OR_PASSWORD.getMessage()));
		response.setStatus(ErrorCode.INVALID_EMAIL_OR_PASSWORD.getHttpStatus().value());
		response.setContentType("application/json");
		response.getWriter().write(responseDto);
		response.getWriter().flush();
		response.getWriter().close();
		throw new CustomException(ErrorCode.INVALID_EMAIL_OR_PASSWORD);

	}
}