package com.kkoch.user.domain.alarm.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.alarm.Alarm;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class AlarmCommandRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AlarmCommandRepository alarmCommandRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원이 열람하지 않은 알림을 모두 열람 샅태로 업데이트하고, 업데이트된 데이터의 수를 반환환다.")
    @Test
    void updateOpen() {
        //given
        Member member = createMember();

        Alarm alarm1 = createAlarm(member, true);
        Alarm alarm2 = createAlarm(member, true);
        Alarm alarm3 = createAlarm(member, false);
        Alarm alarm4 = createAlarm(member, false);
        Alarm alarm5 = createAlarm(member, false);

        //when
        int count = alarmCommandRepository.updateOpen(member.getId());

        //then
        assertThat(count).isEqualTo(3);
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

    private Alarm createAlarm(Member member, boolean open) {
        Alarm alarm = Alarm.builder()
            .content("alarm")
            .open(open)
            .member(member)
            .build();
        return alarmRepository.save(alarm);
    }
}