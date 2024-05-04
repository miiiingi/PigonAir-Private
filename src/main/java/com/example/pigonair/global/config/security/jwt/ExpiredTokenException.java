package com.example.pigonair.global.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends ExpiredJwtException {
	private final String accessToken;
	private final Claims claims;
	private final String email;

	public ExpiredTokenException(Header header, Claims claims, String message, String accessToken, String email) {
		super(header, claims, message);
		this.accessToken = accessToken;
		this.claims = claims;
		this.email = email;
	}
}
