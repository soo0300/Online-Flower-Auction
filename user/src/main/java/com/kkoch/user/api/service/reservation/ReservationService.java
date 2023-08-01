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
        Optional<Member> member = reservationRepository.findByEmail(loginID);
        Long savedId = member.get().getId();
        //memberId를 포함하여 엔티티로 만들어 준다.

        Reservation reservation = dto.toEntity(savedId);

//        if (reservation == null) {
//            throw new IllegalArgumentException("등록을 다시 해주세요");
//        }

        reservationRepository.save(reservation);
        Optional<Reservation> savedReservation = reservationRepository.findById(savedId);
        return savedReservation.get().getId();
    }
}