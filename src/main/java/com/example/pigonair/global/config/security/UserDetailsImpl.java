package com.example.pigonair.global.config.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.pigonair.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetailsImpl implements UserDetails {
	private final Member member;

	public UserDetailsImpl(@JsonProperty("user") Member member) {
		this.member = member;
	}

	public Member getUser() {
		return member;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		return authorities;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}