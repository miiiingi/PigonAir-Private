package com.example.pigonair.domain.reservation.repository;

import java.util.Optional;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  Optional<Reservation> findByMemberId(Long memberId);
    Optional<Reservation> findByMemberId(Long memberId);
    List<Reservation> findByMemberAndIsPayment(Member member, Boolean isPayment);
}
