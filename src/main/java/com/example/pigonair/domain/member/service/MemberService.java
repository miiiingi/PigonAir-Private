package com.example.pigonair.domain.member.service;

import com.example.pigonair.domain.member.dto.MemberRequestDto;

public interface MemberService {
	void signUp(MemberRequestDto.SignupRequestDto requestDto);
}
