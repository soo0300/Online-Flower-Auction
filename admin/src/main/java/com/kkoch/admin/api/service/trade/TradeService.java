package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;
    private final AuctionArticleRepository auctionArticleRepository;

    public Long addTrade(Long memberId, List<AddTradeDto> dto) {
        //PK 추출
        List<Long> articleIds = dto.stream()
                .map(AddTradeDto::getAuctionArticleId)
                .collect(Collectors.toList());

        //경매품 조회
        List<AuctionArticle> auctionArticles = auctionArticleRepository.findByIdIn(articleIds);

        //경매품 리스트 -> 맵으로 변환
        Map<Long, AuctionArticle> auctionArticleMap = auctionArticles.stream()
                .collect(Collectors.toMap(AuctionArticle::getId, auctionArticle -> auctionArticle, (a, b) -> b));

        //경매품 -> 거래내역, 낙찰금액, 낙찰시간 업데이트
        for (AddTradeDto addTradeDto : dto) {
            AuctionArticle auctionArticle = auctionArticleMap.get(addTradeDto.getAuctionArticleId());
            auctionArticle.bid(addTradeDto.getBidPrice(), addTradeDto.getBidTime());
        }

        //낙찰 가격 계산
        int totalPrice = dto.stream()
                .mapToInt(AddTradeDto::getBidPrice)
                .sum();

        //낙찰 내역 생성
        Trade trade = Trade.builder()
                .totalPrice(totalPrice)
                .tradeDate(LocalDateTime.now())
                .pickupStatus(false)
                .active(true)
                .articles(auctionArticles)
                .build();

        //경매품 -> 낙찰 내역 업데이트
        for (Long articleId : articleIds) {
            AuctionArticle auctionArticle = auctionArticleMap.get(articleId);
            auctionArticle.createTrade(trade);
        }

        Trade savedTrade = tradeRepository.save(trade);

        return savedTrade.getId();
    }
}
