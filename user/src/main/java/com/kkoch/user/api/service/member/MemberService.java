package com.kkoch.user.api.service.member;

import com.kkoch.user.api.controller.member.response.TokenResponse;
import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import com.kkoch.user.api.service.member.dto.LoginMemberDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    public Long join(JoinMemberDto dto) {

        Member member = dto.toEntity();

        memberRepository.save(member);

        return member.getId();
    }

    public TokenResponse login(LoginMemberDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!isSamePw(dto.getLoginPw(),member.getLoginPw())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }
        return new TokenResponse(jwtProvider.createToken(member.getEmail(), member.getRoles()));
    }

    public boolean isSamePw(String dtoPW, String domainPw){
        return dtoPW.equals(domainPw);
    }
}
