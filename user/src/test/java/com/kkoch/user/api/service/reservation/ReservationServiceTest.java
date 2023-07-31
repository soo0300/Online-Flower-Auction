package com.kkoch.user.api.service.reservation;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class ReservationServiceTest extends IntegrationTestSupport {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;


    @DisplayName("거래 예약 항목을 입력받아 거래 예약을 등록")
    @Test
    public void addReservation() throws Exception {
        // given
        AddReservationDto dto = AddReservationDto.builder()
                .memberId(0L)
                .plantId(0L)
                .count(10)
                .price(2000)
                .build();

        // when
        Long reservationId = reservationService.addReservation(dto);

        // then
        Optional<Reservation> result = reservationRepository.findById(reservationId);
        Assertions.assertThat(result).isPresent();
    }

}
