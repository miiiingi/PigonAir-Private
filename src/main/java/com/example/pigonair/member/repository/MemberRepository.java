package com.example.pigonair.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pigonair.member.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	// Optional<Member> findByNickname(String nickname);

	boolean existsByEmail(String email);
}

