package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.api.controller.stats.response.AuctionArticleForStatsResponse;
import com.kkoch.admin.domain.plant.repository.StatsQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StatsQueryService {

    private final StatsQueryRepository statsQueryRepository;

    public List<AuctionArticleForStatsResponse> getAuctionList() {
        return statsQueryRepository.findByTime();
    }
}
