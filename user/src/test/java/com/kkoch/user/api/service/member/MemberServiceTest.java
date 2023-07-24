package com.kkoch.user.api.service.member;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.controller.member.response.TokenResponse;
import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import com.kkoch.user.api.service.member.dto.LoginMemberDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import jdk.jshell.spi.ExecutionControl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

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


    @DisplayName("로그인 실패시(아이디, 비밀번호 불일치) 예외처리가 한다.")
    @Test
    void loginFail() throws Exception {
        //given
        String email = "test@test.com";
        String loginPw = "4321";
        String wrongLoginPw = "1234";

        Long memberId = joinMember(email, loginPw);
        LoginMemberDto loginMemberDto = createLoginMemberDto(email, wrongLoginPw);
        //when, then

        Assertions.assertThatThrownBy(() -> memberService.login(loginMemberDto))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("잘못된 계정입니다.");

    }

    @DisplayName("로그인 성공시 JWT를 반환한다.")
    @Test
    void loginSucess() throws Exception {
        //given
        String email = "test@test.com";
        String loginPw = "1234";

        Long memberId = joinMember(email, loginPw);
        LoginMemberDto loginMemberDto = createLoginMemberDto(email, loginPw);
        //when, then

        String token = memberService.login(loginMemberDto);
        Assertions.assertThat(token).isNotEmpty();
    }

    private Long joinMember(String email, String loginPw) {
        JoinMemberDto member = JoinMemberDto.builder()
                .email(email)
                .loginPw(loginPw)
                .name("test")
                .tel("010-4321-5678")
                .businessNumber("C1234512345B")
                .point(0)
                .active(true)
                .build();

        return memberService.join(member);
    }

    private LoginMemberDto createLoginMemberDto(String email, String loginPw) {
        return LoginMemberDto.builder()
                .email(email)
                .loginPw(loginPw)
                .build();
    }


}
