package com.kkoch.user;

import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        initMember();
    }

    private void initMember() {
        Member member1 = createMember("ssafy1@ssafy.com", "pw1!", "임우택", "010-1111-1111", "111-11-11111", 10000);
        Member member2 = createMember("ssafy2@ssafy.com", "pw2!", "이예리", "010-2222-2222", "222-22-22222", 20000);
        Member member3 = createMember("ssafy3@ssafy.com", "pw3!", "홍승준", "010-3333-3333", "333-33-33333", 30000);
        Member member4 = createMember("ssafy4@ssafy.com", "pw4!", "김수진", "010-4444-4444", "444-44-44444", 40000);
        Member member5 = createMember("ssafy5@ssafy.com", "pw5!", "신성주", "010-5555-5555", "555-55-55555", 50000);
        Member member6 = createMember("ssafy6@ssafy.com", "pw6!", "서용준", "010-6666-6666", "666-66-66666", 60000);
        List<Member> members = List.of(member1, member2, member3, member4, member5, member6);
        memberRepository.saveAll(members);
    }

    private Member createMember(String email, String loginPw, String name, String tel, String businessNumber, int point) {
        return Member.builder()
            .email(email)
            .loginPw(loginPw)
            .name(name)
            .tel(tel)
            .businessNumber(businessNumber)
            .point(point)
            .active(true)
            .build();
    }
}
