package com.kkoch.user.api.service.reservation;

import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final MemberRepository memberRepository;

    //거래 예약 등록
    public Long addReservation(String loginID, AddReservationDto dto) {
        Member member = memberRepository.findByEmail(loginID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 거래 예약입니다."));

        //memberId를 포함하여 엔티티로 만들어 준다.
        Reservation reservation = dto.toEntity(member);

        Reservation savedReservation = reservationRepository.save(reservation);

        return savedReservation.getId();
    }

    //거래 예약 삭제
    public Long removeReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 수 없는 거래 예약입니다."));

        reservationRepository.delete(reservation);

        return reservation.getId();
    }

}