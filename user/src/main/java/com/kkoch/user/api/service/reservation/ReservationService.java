package com.kkoch.user.api.service.reservation;

import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    //거래 예약 등록
    //서비스 단에서 dto로 받아와서 entity로 repo에게 전달한다.
    public Long addReservation(AddReservationDto dto) {
        Reservation reservation = dto.toEntity();
        reservationRepository.save(reservation);
        return reservation.getId();
    }


}