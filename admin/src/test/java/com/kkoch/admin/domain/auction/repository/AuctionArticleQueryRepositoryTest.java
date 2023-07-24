package com.kkoch.admin.domain.auction.repository;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.trade.response.AuctionArticleResponse;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.plant.Category;
import com.kkoch.admin.domain.plant.Plant;
import com.kkoch.admin.domain.plant.repository.CategoryRepository;
import com.kkoch.admin.domain.plant.repository.PlantRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
class AuctionArticleQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AuctionArticleQueryRepository auctionArticleQueryRepository;

    @Autowired
    private AuctionArticleRepository auctionArticleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private TradeRepository tradeRepository;

    @DisplayName("")
    @Test
    void findByTradeId() {
        //given
        Category code = createCategory("절화", null);
        Category name = createCategory("장미(스탠다드)", code);
        Category type = createCategory("하젤", name);

        Plant plant = createPlant(code, name, type);

        Trade trade = createTrade();

        AuctionArticle auctionArticle1 = createAuctionArticle("00001", LocalDate.of(2023, 7, 10).atStartOfDay(), plant, trade);
        AuctionArticle auctionArticle2 = createAuctionArticle("00002", LocalDate.of(2023, 7, 10).atStartOfDay(), plant, trade);
        AuctionArticle auctionArticle3 = createAuctionArticle("00003", LocalDate.of(2023, 7, 10).atStartOfDay(), plant, trade);

        //when
        List<AuctionArticleResponse> responses = auctionArticleQueryRepository.findByTradeId(trade.getId());

        //then
        assertThat(responses).hasSize(3)
                .extracting("code", "name", "type")
                .containsExactlyInAnyOrder(
                        tuple(code.getName(), name.getName(), type.getName()),
                        tuple(code.getName(), name.getName(), type.getName()),
                        tuple(code.getName(), name.getName(), type.getName())
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
                .tradeDate(LocalDate.of(2023, 7, 11).atStartOfDay())
                .pickupStatus(false)
                .active(true)
                .memberId(1L)
                .build();
        return tradeRepository.save(trade);
    }

    private AuctionArticle createAuctionArticle(String auctionNumber, LocalDateTime bidTime, Plant plant, Trade trade) {
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