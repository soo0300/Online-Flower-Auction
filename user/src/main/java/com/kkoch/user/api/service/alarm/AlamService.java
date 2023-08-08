package com.kkoch.user.api.service.alarm;

import com.kkoch.user.domain.alarm.repository.AlarmCommandRepository;
import com.kkoch.user.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AlamService {

    private final AlarmCommandRepository alarmCommandRepository;
    private final MemberRepository memberRepository;

    public int open(String memberKey) {
        return alarmCommandRepository.updateOpen(memberKey);
    }
}
