package com.example.pigonair.global.config.security.jwt;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.pigonair.domain.member.dto.MemberRequestDto;
import com.example.pigonair.global.config.common.exception.CustomException;
import com.example.pigonair.global.config.common.exception.ErrorCode;
import com.example.pigonair.global.config.jmeter.JmeterService;
import com.example.pigonair.global.config.security.UserDetailsImpl;
import com.example.pigonair.global.config.security.refreshtoken.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtUtil jwtUtil;
	private final TokenService tokenService;
	private final JmeterService jmeterService;

	public JwtAuthenticationFilter(JwtUtil jwtUtil,
		TokenService tokenService, JmeterService jmeterService) {
		this.jwtUtil = jwtUtil;
		this.jmeterService = jmeterService;
		this.tokenService = tokenService;
		setFilterProcessesUrl("/loginProcess");
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
		jmeterService.setTransactionNameBasedOnJMeterTag(request);
		String email = ((UserDetailsImpl)authResult.getPrincipal()).getUsername();
		String accessToken = jwtUtil.createToken(email);
		String refreshToken = jwtUtil.createRefreshToken();
		tokenService.setRefreshToken(accessToken, refreshToken, email);
		jwtUtil.addJwtToCookie(accessToken, response);
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		log.info("로그인 실패");
		jmeterService.setTransactionNameBasedOnJMeterTag(request);
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