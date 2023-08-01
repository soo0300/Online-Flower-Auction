package com.kkoch.user.api.service.reservation;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.service.reservation.dto.AddReservationDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
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

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("거래 예약 항목을 입력받아 거래 예약을 등록")
    @Test
    public void addReservation() throws Exception {
        // given
        AddReservationDto dto = AddReservationDto.builder()
                .plantId(1L)
                .count(10)
                .price(2000)
                .build();

        Member savedMember = insertMember();

        // when
        Long reservationId = reservationService.addReservation(savedMember.getEmail(), dto);

        // then
        Optional<Reservation> result = reservationRepository.findById(reservationId);
        Assertions.assertThat(result).isPresent();
    }

    private Member insertMember() {
        Member member = Member.builder()
                .email("soo")
                .point(2)
                .tel("01092399292")
                .loginPw("123")
                .active(true)
                .name("soojin")
                .businessNumber("123")
                .build();

        return memberRepository.save(member);
    }

}
