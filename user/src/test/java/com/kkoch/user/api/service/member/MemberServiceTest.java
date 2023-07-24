package com.kkoch.user.api.service.member;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원 정보를 입력 받아 회원 가입 성공")
    @Test
    void join() throws Exception {
        //given
        JoinMemberDto member = JoinMemberDto.builder()
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

        //then
        Optional<Member> result = memberRepository.findById(saveId);
        Assertions.assertThat(result).isPresent();
    }
}