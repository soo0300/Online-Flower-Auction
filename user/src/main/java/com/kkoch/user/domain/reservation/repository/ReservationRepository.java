package com.kkoch.user.domain.reservation.repository;

import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByMember(Member member);
}
