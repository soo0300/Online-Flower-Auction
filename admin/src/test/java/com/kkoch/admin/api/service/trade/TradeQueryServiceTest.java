package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
class TradeQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private TradeQueryService tradeQueryService;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private AuctionArticleRepository auctionArticleRepository;

    @DisplayName("회원은 본인의 낙찰 내역을 기간으로 조회를 할 수 있다.")
    @Test
    void getMyTrades() {
        //given
        LocalDate currentDate = LocalDate.of(2023, 7, 10);

        Trade trade1 = createTrade(1L, 3000, LocalDateTime.of(2023, 7, 3, 23, 59, 59), true);
        Trade trade2 = createTrade(1L, 3000, LocalDateTime.of(2023, 7, 4, 0, 0, 0), true);
        Trade trade3 = createTrade(1L, 3000, LocalDateTime.of(2023, 7, 7, 0, 0, 0), false);
        Trade trade4 = createTrade(2L, 3000, LocalDateTime.of(2023, 7, 7, 0, 0, 0), true);
        Trade trade5 = createTrade(1L, 2000, LocalDateTime.of(2023, 7, 10, 23, 59, 59), true);

        createAuctionArticle(trade2, "00001");
        createAuctionArticle(trade2, "00002");
        createAuctionArticle(trade2, "00003");
        createAuctionArticle(trade5, "00004");
        createAuctionArticle(trade5, "00005");

        TradeSearchCond cond = TradeSearchCond.of(currentDate, 7);
        PageRequest pageRequest = PageRequest.of(0, 20);

        //when
        Page<TradeResponse> result = tradeQueryService.getMyTrades(1L, cond, pageRequest);

        //then
        List<TradeResponse> responses = result.getContent();
        assertThat(responses).hasSize(2)
                .extracting("totalPrice", "count")
                .containsExactlyInAnyOrder(
                        tuple(3000, 3),
                        tuple(2000, 2)
                );
    }

    private AuctionArticle createAuctionArticle(Trade trade, String auctionNumber) {
        AuctionArticle auctionArticle = AuctionArticle.builder()
                .trade(trade)
                .auctionNumber(auctionNumber)
                .grade(Grade.NONE)
                .count(10)
                .bidPrice(1000)
                .bidTime(LocalDateTime.now())
                .region("광주")
                .shipper("김싸피")
                .startPrice(5000)
                .build();
        return auctionArticleRepository.save(auctionArticle);
    }

    private Trade createTrade(Long memberId, int totalPrice, LocalDateTime tradeDate, boolean active) {
        Trade trade = Trade.builder()
                .memberId(memberId)
                .totalPrice(totalPrice)
                .tradeDate(tradeDate)
                .pickupStatus(false)
                .active(active)
                .build();
        return tradeRepository.save(trade);
    }
}