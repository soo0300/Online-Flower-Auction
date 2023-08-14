package com.kkoch.user.api.service.pointhistory;

import com.kkoch.user.api.controller.pointhistory.response.AddPointHistoryResponse;
import com.kkoch.user.api.service.pointhistory.dto.AddPointHistoryDto;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.pointhistory.PointHistory;
import com.kkoch.user.domain.pointhistory.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PointHistoryService {

    private final PointHistoryRepository pointHistoryRepository;
    private final MemberRepository memberRepository;

    public AddPointHistoryResponse addPointHistory(String memberKey, AddPointHistoryDto dto) {
        Member member = memberRepository.findByMemberKey(memberKey)
            .orElseThrow(NoSuchElementException::new);

        PointHistory pointHistory = dto.toEntity(member);
        PointHistory savedPointHistory = pointHistoryRepository.save(pointHistory);
        return AddPointHistoryResponse.of(savedPointHistory);
    }
}
