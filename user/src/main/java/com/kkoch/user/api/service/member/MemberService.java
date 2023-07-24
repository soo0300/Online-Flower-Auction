package com.kkoch.user.api.service.member;

import com.kkoch.user.api.service.member.dto.JoinMemberDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public Long join(JoinMemberDto dto) {

        Member member = dto.toEntity();

        memberRepository.save(member);

        return member.getId();
    }
}
