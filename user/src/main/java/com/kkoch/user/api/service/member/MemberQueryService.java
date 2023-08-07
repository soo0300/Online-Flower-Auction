package com.kkoch.user.api.service.member;

import com.kkoch.user.api.controller.member.response.MemberInfoResponse;
import com.kkoch.user.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberInfoResponse getMyInfo(String memberKey) {
        return memberQueryRepository.findMyInfoByMemberKey(memberKey);
    }
}
