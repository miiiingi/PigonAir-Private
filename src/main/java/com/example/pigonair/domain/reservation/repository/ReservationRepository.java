package com.example.pigonair.domain.reservation.repository;

import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberAndIsPayment(Member member, Boolean isPayment);
	Optional<Reservation> findByMemberId(Long memberId);

    List<Reservation> findByIsPaymentFalseAndReservationDateBefore(LocalDateTime cutoffDate);

}
