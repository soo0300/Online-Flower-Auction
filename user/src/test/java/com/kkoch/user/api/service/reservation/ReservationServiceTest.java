package com.kkoch.user.api.service.reservation;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
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

    @DisplayName("거래 예약을 삭제할 수 있다.")
    @Test
    public void removeReservation() throws Exception {
        //given
        Reservation reservation = insertReservation();

        // when
        reservationService.removeReservation(reservation.getId());

        // then
        Optional<Reservation> reser = reservationRepository.findById(reservation.getId());
        Assertions.assertThat(reser).isNotPresent();

    }

    @DisplayName("회원은 자신이 예약한 거래 예약을 조회할 수 있다")
    @Test
    public void getReservation() throws Exception {
        // given
        Reservation reservation = insertReservation();
        String loiginId = reservation.getMember().getEmail();

        // when
        ReservationResponse response = reservationService.getReservation(loiginId);

        // then
        Assertions.assertThat(response.getPrice()).isEqualTo(1000);
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

    private Reservation insertReservation() {
        Reservation reservation = Reservation.builder()
                .plantId(1L)
                .count(1)
                .member(insertMember())
                .price(1000)
                .build();

        return reservationRepository.save(reservation);

    }
}