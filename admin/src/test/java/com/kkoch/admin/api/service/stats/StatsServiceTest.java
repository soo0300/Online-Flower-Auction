package com.kkoch.admin.api.service.stats;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.stats.dto.AuctionArticleForStatsDto;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.plant.Stats;
import com.kkoch.admin.domain.plant.repository.StatsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class StatsServiceTest extends IntegrationTestSupport {

    @Autowired
    StatsService statsService;

    @Autowired
    StatsRepository statsRepository;

    @DisplayName("전일 낙찰된 경매품의 평균 단가를 계산해 낙찰 통계에 저장한다.")
    @Test
    void addStats() throws Exception {
        //given
        List<AuctionArticleForStatsDto> dto = createAuctionArticleList();

        //when
        Long statsId = statsService.addStats(dto);

        //then
        Optional<Stats> result = statsRepository.findById(statsId);
        Assertions.assertThat(result).isPresent();
    }

    private List<AuctionArticleForStatsDto> createAuctionArticleList() {
        List<AuctionArticleForStatsDto> lists = new ArrayList<>();
        lists.add(createDto(1L, Grade.Advanced, 1000, 10));
        lists.add(createDto(1L, Grade.Super, 1500, 10));
        lists.add(createDto(1L, Grade.Super, 1500, 10));
        lists.add(createDto(1L, Grade.Normal, 1000, 10));
        lists.add(createDto(2L, Grade.Super, 1000, 10));
        lists.add(createDto(2L, Grade.Super, 1000, 10));
        lists.add(createDto(2L, Grade.Super, 1000, 10));

        return lists;
    }

    private AuctionArticleForStatsDto createDto(Long plantId, Grade grade, int bidPrice, int count) {
        return AuctionArticleForStatsDto.builder()
                .plantId(plantId)
                .grade(grade)
                .bidPrice(bidPrice)
                .count(count)
                .build();
    }


}