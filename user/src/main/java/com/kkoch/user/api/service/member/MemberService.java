package com.kkoch.user.api.service.member;

import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long join(JoinMemberDto dto) {
        dto.setMemberKey(UUID.randomUUID().toString());
        Member member = dto.toEntity(passwordEncoder.encode(dto.getPwd()));

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

//    public TokenResponse login(LoginMemberDto dto) {
//        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
//                new BadCredentialsException("잘못된 계정정보입니다."));
//
//        if (!isSamePw(dto.getLoginPw(),member.getLoginPw())) {
//            throw new BadCredentialsException("잘못된 계정정보입니다.");
//        }
//        return new TokenResponse(jwtProvider.createToken(member.getEmail(), member.getRoles()));
//    }

    public boolean isSamePw(String dtoPW, String domainPw){
        return dtoPW.equals(domainPw);
    }
}
