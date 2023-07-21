package com.kkoch.user.domain.member.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@Transactional
class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("member repository test")
    @Test
    void addMember() throws Exception {

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
        Member result = memberRepository.save(member);

        //then
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getEmail()).isEqualTo("test@test.net");
        Assertions.assertThat(result.getLoginPw()).isEqualTo("1234");
        Assertions.assertThat(result).isEqualTo(member);

    }

    @DisplayName("회원이 존재하는지 테스트")
    @Test
    void checkDuplication() throws Exception {

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
        memberRepository.save(member);
        Member findResult = memberRepository.findByEmail("test@test.net");

        //then
        Assertions.assertThat(findResult.getId()).isNotNull();
        Assertions.assertThat(findResult.getEmail()).isEqualTo("test@test.net");
        Assertions.assertThat(findResult.getLoginPw()).isEqualTo("1234");
        Assertions.assertThat(findResult).isEqualTo(member);
    }
}