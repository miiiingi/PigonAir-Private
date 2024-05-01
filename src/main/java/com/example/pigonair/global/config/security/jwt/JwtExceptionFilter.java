package com.example.pigonair.global.config.security.jwt;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.pigonair.global.config.common.exception.ErrorCode;
import com.example.pigonair.global.config.security.refreshtoken.TokenService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 인증 에러 처리")
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		/*
		1. 리프레쉬 토큰이 존재하는지 검증
			만약 리프레쉬 토큰이 만료 등의 이유로 존재하지 않는다면 서버에는 500 에러 던지고 웹페이지는 에러 페이지로 연결
			만약 리프레쉬 토큰이 정상적으로 존재한다면, 리프레쉬 토큰의 정보를 반환
		2. 리프레쉬 토큰과 함께 제공된 이메일을 바탕으로 새로운 액세스 토큰을 발급
		3. 새로운 액세스 토큰과 기존 리프레쉬 토큰을 연결
		4. HttpServletResponse 에 쿠키와 헤더를 새롭게 갱신하고 원래 접속 중이던 페이지로 리다이렉트
		 */
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (ExpiredTokenException e) {
			String refreshToken = tokenService.getRefreshTokenInfo(e.getAccessToken()).get("refreshToken");
			String newAccessToken = jwtUtil.createToken(e.getEmail());
			tokenService.setRefreshToken(newAccessToken, refreshToken, e.getEmail());
			jwtUtil.addJwtToCookie(newAccessToken, response);
			response.addHeader(JwtUtil.AUTHORIZATION_HEADER, newAccessToken);
			response.sendRedirect(request.getRequestURL().toString());

		} catch (MalformedJwtException | SecurityException e) {
			handleException(response, "Invalid token format.", "/login-page");
		} catch (UnsupportedJwtException | IllegalArgumentException e) {
			handleException(response, "Token not supported.", "/error-page");
		} catch (ExpiredJwtException e) {
			handleException(response, ErrorCode.REFRESH_TOKEN_ERROR.getMessage(), "/login-page");
		} catch (Exception e) {
			handleException(response, "An internal error occurred.", "/error-page");
		}
	}

	private void handleException(HttpServletResponse response, String message, String redirectPath) throws IOException {
		log.error("JWT token validation error: " + message);
		clearCookies(response);
		redirect(response, redirectPath);
	}

	private void clearCookies(HttpServletResponse response) {
		response.addHeader("Set-Cookie", "Authorization=; Path=/; HttpOnly; Expires=Thu, 01 Jan 1970 00:00:00 GMT");
	}

	private void redirect(HttpServletResponse response, String path) throws IOException {
		if (!response.isCommitted()) {
			response.sendRedirect(path);
		}
	}

}
