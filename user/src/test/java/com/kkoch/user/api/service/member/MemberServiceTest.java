package com.kkoch.user.api.service.member;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
public class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        //given
        Member member = Member.builder()
                .email("test@test.net")
                .loginPw("1234")
                .name("hong")
                .tel("010-1234-5678")
                .businessNumber("A1234512345B")
                .point(0)
                .active(true)
                .build();

        //when
        Long saveId = memberService.join(member);
        Member result = memberRepository.findById(saveId).get();

        //then
        Assertions.assertThat(member).isEqualTo(result);
    }
}