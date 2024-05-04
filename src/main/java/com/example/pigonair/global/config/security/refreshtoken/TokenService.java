package com.example.pigonair.global.config.security.refreshtoken;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.pigonair.global.config.security.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Token Service")
public class TokenService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final JwtUtil jwtUtil;

	public void setRefreshToken(String accessToken, String refreshToken, String email) {
		accessToken = jwtUtil.substringToken(accessToken);
		Map<String, String> tokenInfo = new HashMap<>();
		tokenInfo.put("refreshToken", refreshToken);
		tokenInfo.put("email", email);

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String tokenInfoJson = objectMapper.writeValueAsString(tokenInfo);
			redisTemplate.opsForValue()
				.set(accessToken, tokenInfoJson, Duration.ofSeconds(jwtUtil.getRefreshExpirationTime() / 1000));
		} catch (JsonProcessingException e) {
			log.error("Error while serializing token info", e);
		}
	}

	public Map<String, String> getRefreshTokenInfo(String accessToken) {
		/*
		액세스 토큰을 통해서 리프레쉬 토큰이 존재하는지 검증
		만약 리프레쉬 토큰이 만료 등의 이유로 존재하지 않는다면 서버에는 500 에러 던지고 웹페이지는 에러 페이지로 연결
		만약 리프레쉬 토큰이 정상적으로 존재한다면, 리프레쉬 토큰의 정보를 반환
		 */
		String tokenInfoJson = (String)redisTemplate.opsForValue().get(accessToken);
		jwtUtil.validateRefreshToken(tokenInfoJson);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(tokenInfoJson, new TypeReference<Map<String, String>>() {
			});
		} catch (IOException e) {
			log.error("Error while deserializing token info", e);
			return null;
		}
	}

	public void removeTokenInfo(String accessToken) {
		accessToken = jwtUtil.substringToken(accessToken);
		redisTemplate.delete(accessToken);
		log.info("Removed token information for access token: {}", accessToken);
	}

}