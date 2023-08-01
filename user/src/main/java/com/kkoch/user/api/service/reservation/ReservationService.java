package com.kkoch.user.api.service.reservation;

import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    //거래 예약 등록
    public Long addReservation(String loginID, AddReservationDto dto) {
        Reservation reservation = dto.toEntity();
        Optional<Member> member = reservationRepository.findByLoginId(loginID);
        Long savedId = member.get().getId();
        reservationRepository.save(reservation);
        Optional<Reservation> savedReservation = reservationRepository.findById(dto.getMemberId());
        return savedReservation.get().getId();
    }
}