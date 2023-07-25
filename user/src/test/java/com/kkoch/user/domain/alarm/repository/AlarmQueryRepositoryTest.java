package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class AlarmQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AlarmQueryRepository alarmQueryRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원은 본인의 알림을 최신순으로 10건 조회를 할 수 있다.")
    @Test
    void searchAlarm() {
        //given
        Member member = createMember();

        Alarm alarm1 = createAlarm(member, "예약하신 장미 10단이 5,000원에 낙찰되셨습니다.", true);
        Alarm alarm2 = createAlarm(member, "예약하신 장미 20단이 5,000원에 낙찰되셨습니다.", false);
        Alarm alarm3 = createAlarm(member, "예약하신 장미 30단이 5,000원에 낙찰되셨습니다.", false);

        //when
        List<AlarmResponse> responses = alarmQueryRepository.searchAlarms(member.getId());

        //then
        Assertions.assertThat(responses).hasSize(3)
            .extracting("content", "open")
            .containsExactlyInAnyOrder(
                tuple(alarm1.getContent(), alarm1.isOpen()),
                tuple(alarm2.getContent(), alarm2.isOpen()),
                tuple(alarm3.getContent(), alarm3.isOpen())
            );
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("ssafy@ssafy.com")
            .loginPw("ssafy1234@")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(0)
            .active(true)
            .build();
        return memberRepository.save(member);
    }

    private Alarm createAlarm(Member member, String content, boolean open) {
        Alarm alarm = Alarm.builder()
            .content(content)
            .open(open)
            .member(member)
            .build();
        return alarmRepository.save(alarm);
    }
}