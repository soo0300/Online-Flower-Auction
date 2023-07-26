package com.kkoch.user.api.service.alarm;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.alarm.repository.AlarmRepository;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class AlarmQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AlarmQueryService alarmQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @DisplayName("회원은 본인의 최신 알림 10건을 조회할 수 있다.")
    @Test
    void test() {
        //given
        Member member = createMember();

        Alarm alarm1 = createAlarm("alarm1", member);
        Alarm alarm2 = createAlarm("alarm2", member);
        Alarm alarm3 = createAlarm("alarm3", member);
        Alarm alarm4 = createAlarm("alarm4", member);
        Alarm alarm5 = createAlarm("alarm5", member);

        //when
        List<AlarmResponse> responses = alarmQueryService.searchAlarms(member.getEmail());

        //then
        assertThat(responses).hasSize(5)
            .extracting("content")
            .containsExactlyInAnyOrder(
                tuple("alarm1"),
                tuple("alarm2"),
                tuple("alarm3"),
                tuple("alarm4"),
                tuple("alarm5")
            );
    }

    private Alarm createAlarm(String content, Member member) {
        Alarm alarm = Alarm.builder()
            .content(content)
            .open(true)
            .member(member)
            .build();
        return alarmRepository.save(alarm);
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("ssafy@ssafy.com")
            .loginPw("ssafy1234!@")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(0)
            .active(true)
            .build();
        return memberRepository.save(member);
    }
}