package com.kkoch.user.domain.member.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired private MemberRepository memberRepository;

    @DisplayName("member repository test")
    @Test
    public void addMember() throws Exception {
        //given
        final Member member = Member.builder()
                .email("test@test.net")
                .loginPw("1234")
                .name("hong")
                .tel("010-1234-5678")
                .businessNumber("A1234512345B")
                .point(0)
                .active(true)
                .build();

        //when
        final Member result = memberRepository.save(member);

        //then
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo("test@test.net");
        Assertions.assertThat(result.getLoginPw()).isEqualTo("1234");
        Assertions.assertThat(result).isEqualTo(member);

    }

}