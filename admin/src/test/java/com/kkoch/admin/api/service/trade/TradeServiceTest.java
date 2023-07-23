package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.IntegrationTestSupport;
import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Transactional
class TradeServiceTest extends IntegrationTestSupport {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private AuctionArticleRepository auctionArticleRepository;

    @DisplayName("경매 종료 후 낙찰 정보가 들어오면 낙찰 내역이 생성된다.")
    @Test
    void addTrade() {
        //given
        AuctionArticle auctionArticle1 = createAuctionArticle("00001");
        AuctionArticle auctionArticle2 = createAuctionArticle("00002");
        AuctionArticle auctionArticle3 = createAuctionArticle("00003");

        AddTradeDto dto1 = createAddTradeDto(auctionArticle1.getId(), 2500);
        AddTradeDto dto2 = createAddTradeDto(auctionArticle2.getId(), 3000);
        AddTradeDto dto3 = createAddTradeDto(auctionArticle3.getId(), 3500);
        List<AddTradeDto> dtos = List.of(dto1, dto2, dto3);

        //when
        Long tradeId = tradeService.addTrade(1L, dtos);

        //then
        Optional<Trade> findTrade = tradeRepository.findById(tradeId);
        assertThat(findTrade).isPresent();
        assertThat(findTrade.get().getTotalPrice()).isEqualTo(9000);
        assertThat(findTrade.get().getArticles())
                .hasSize(3)
                .extracting("trade.id", "bidPrice")
                .containsExactlyInAnyOrder(
                        tuple(findTrade.get().getId(), 2500),
                        tuple(findTrade.get().getId(), 3000),
                        tuple(findTrade.get().getId(), 3500)
                );
    }

    private AuctionArticle createAuctionArticle(String auctionNumber) {
        AuctionArticle auctionArticle = AuctionArticle.builder()
                .auctionNumber(auctionNumber)
                .grade(Grade.NONE)
                .count(10)
                .bidPrice(0)
                .bidTime(LocalDateTime.now())
                .region("광주")
                .shipper("김싸피")
                .startPrice(5_000)
                .build();
        return auctionArticleRepository.save(auctionArticle);
    }

    private AddTradeDto createAddTradeDto(Long auctionArticleId, int bidPrice) {
        return AddTradeDto.builder()
                .auctionArticleId(auctionArticleId)
                .bidPrice(bidPrice)
                .bidTime(LocalDateTime.now())
                .build();
    }
}