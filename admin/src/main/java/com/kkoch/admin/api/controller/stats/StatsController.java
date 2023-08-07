package com.kkoch.admin.api.controller.stats;

import com.kkoch.admin.api.controller.stats.request.StatsRequest;
import com.kkoch.admin.api.controller.stats.response.StatsResponse;
import com.kkoch.admin.api.service.stats.StatsQueryService;
import com.kkoch.admin.api.service.stats.StatsService;
import com.kkoch.admin.api.service.stats.dto.AuctionArticleForStatsDto;
import com.kkoch.admin.domain.plant.repository.StatsSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin-service/stats")
@Slf4j
public class StatsController {

    private final StatsQueryService statsQueryService;

    private final StatsService statsService;

    @Scheduled(cron = "0 0 10 * * *") // 매일 오전 10시에 실행/../
    public void executeAddStats() {
        List<AuctionArticleForStatsDto> articles = statsQueryService.getAuctionList();
        statsService.saveHistoryBidStats(articles);
    }

    @GetMapping
    public List<StatsResponse> getStats(@Valid @RequestBody StatsRequest statsRequest) {
        StatsSearchCond cond = statsRequest.toStatsSearchCond();
        return statsQueryService.getStatsByCond(cond);
    }
}
