package com.example.pigonair.domain.reservation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.reservation.entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@EntityGraph(attributePaths = {"seat", "flight", "member"})
	List<Reservation> findAllByMemberId(Long memberId);

	List<Reservation> findByIsPaymentFalseAndReservationDateBefore(LocalDateTime cutoffDate);

	@Query("SELECT r.id, f.departureTime, f.origin, f.destination, s.number, s.price " +
		"FROM Reservation r " +
		"JOIN r.flight f " +
		"JOIN r.seat s " +
		"WHERE r.member = :member AND r.isPayment = :isPayment")
	List<Object[]> findReservationInfoByMemberAndIsPaymentWithFlightAndSeat(@Param("member") Member member,
		@Param("isPayment") boolean isPayment);

}
