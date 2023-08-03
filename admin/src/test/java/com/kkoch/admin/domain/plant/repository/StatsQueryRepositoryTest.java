package com.kkoch.admin.domain.plant.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.stats.response.AuctionArticleForStatsResponse;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.auction.repository.AuctionRepository;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;


class StatsQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    StatsQueryRepository statsQueryRepository;

    @Autowired
    AuctionArticleRepository auctionArticleRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PlantRepository plantRepository;

    @DisplayName("현재 시간부터 현재시간 12시간 전까지의 낙찰된 경매품을 조회한다.")
    @Test
    void getAuctionArticleByCond() throws Exception {
        //given
        Category code = createCategory("절화", null);
        Category name = createCategory("장미(스탠다드)", code);
        Category type1 = createCategory("하젤", name);
        Category type2 = createCategory("클레라", name);

        Plant plant1 = createPlant(code, name, type1);
        Plant plant2 = createPlant(code, name, type2);

        Trade trade = createTrade();

        AuctionArticle auctionArticle1 = createAuctionArticle("00001", LocalDateTime.now().minusHours(11), Grade.Super, plant1, trade);
        AuctionArticle auctionArticle2 = createAuctionArticle("00002", LocalDateTime.now().minusHours(12), Grade.Super, plant1, trade);
        AuctionArticle auctionArticle3 = createAuctionArticle("00003", LocalDateTime.now().minusHours(13), Grade.Advanced, plant1, trade);
        AuctionArticle auctionArticle4 = createAuctionArticle("00004", LocalDateTime.now().minusHours(13), Grade.Super, plant2, trade);
        AuctionArticle auctionArticle5 = createAuctionArticle("00005", LocalDateTime.now().minusHours(11), Grade.Advanced, plant2, trade);

        //when
        List<AuctionArticleForStatsResponse> responses = statsQueryRepository.findByBidTime();

        //then
        assertThat(responses).hasSize(3)
                .extracting("plantId", "grade", "count", "bidPrice", "bidTime")
                .containsExactlyInAnyOrder(
                        tuple(auctionArticle1.getPlant().getId(), auctionArticle1.getGrade().name(), auctionArticle1.getCount(), auctionArticle1.getBidPrice(), auctionArticle1.getBidTime()),
                        tuple(auctionArticle2.getPlant().getId(), auctionArticle2.getGrade().name(), auctionArticle2.getCount(), auctionArticle2.getBidPrice(), auctionArticle2.getBidTime()),
                        tuple(auctionArticle5.getPlant().getId(), auctionArticle5.getGrade().name(), auctionArticle5.getCount(), auctionArticle5.getBidPrice(), auctionArticle5.getBidTime())

                );

    }

    private Category createCategory(String name, Category parent) {
        Category category = Category.builder()
                .name(name)
                .active(true)
                .parent(parent)
                .build();
        return categoryRepository.save(category);
    }

    private Plant createPlant(Category code, Category name, Category type) {
        Plant plant = Plant.builder()
                .active(true)
                .code(code)
                .name(name)
                .type(type)
                .build();
        return plantRepository.save(plant);
    }

    private Trade createTrade() {
        Trade trade = Trade.builder()
                .totalPrice(9000)
                .tradeTime(LocalDate.of(2023, 7, 11).atStartOfDay())
                .pickupStatus(false)
                .active(true)
                .memberId(1L)
                .build();
        return tradeRepository.save(trade);
    }

    private AuctionArticle createAuctionArticle(String auctionNumber, LocalDateTime bidTime, Grade grade, Plant plant, Trade trade) {
        AuctionArticle auctionArticle = AuctionArticle.builder()
                .auctionNumber(auctionNumber)
                .grade(Grade.NONE)
                .count(10)
                .bidPrice(3000)
                .bidTime(bidTime)
                .region("광주")
                .shipper("김싸피")
                .startPrice(5000)
                .plant(plant)
                .trade(trade)
                .build();
        return auctionArticleRepository.save(auctionArticle);
    }

}