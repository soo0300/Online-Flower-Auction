package com.kkoch.user.api.service.pointhistory;

import com.kkoch.user.api.controller.pointhistory.response.PointHistoryResponse;
import com.kkoch.user.domain.pointhistory.repository.PointHistoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PointHistoryQueryService {

    private final PointHistoryQueryRepository pointHistoryQueryRepository;

    public Page<PointHistoryResponse> getMyPointHistories(String memberKey, Pageable pageable) {
        List<PointHistoryResponse> content = pointHistoryQueryRepository.findByMemberKey(memberKey, pageable);
        long totalCount = pointHistoryQueryRepository.getTotalCount(memberKey);
        return new PageImpl<>(content, pageable, totalCount);
    }
}
