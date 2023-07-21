package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import com.kkoch.admin.domain.auction.AuctionArticle;
import com.kkoch.admin.domain.auction.repository.AuctionArticleRepository;
import com.kkoch.admin.domain.trade.Trade;
import com.kkoch.admin.domain.trade.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<AuctionArticle> auctionArticles = getAuctionArticles(dto);
        updateBidInfo(dto, auctionArticles);

        int totalPrice = getTotalPrice(dto);

        Trade trade = Trade.createTrade(totalPrice, memberId, auctionArticles);
        Trade savedTrade = tradeRepository.save(trade);

        return savedTrade.getId();
    }

    private List<AuctionArticle> getAuctionArticles(List<AddTradeDto> dto) {
        List<Long> articleIds = dto.stream()
                .map(AddTradeDto::getAuctionArticleId)
                .collect(Collectors.toList());

        return auctionArticleRepository.findByIdIn(articleIds);
    }

    private void updateBidInfo(List<AddTradeDto> dto, List<AuctionArticle> auctionArticles) {
        Map<Long, AuctionArticle> auctionArticleMap = auctionArticles.stream()
                .collect(Collectors.toMap(AuctionArticle::getId, auctionArticle -> auctionArticle, (a, b) -> b));

        dto.forEach(addTradeDto -> {
            AuctionArticle auctionArticle = auctionArticleMap.get(addTradeDto.getAuctionArticleId());
            auctionArticle.bid(addTradeDto.getBidPrice(), addTradeDto.getBidTime());
        });
    }

    private int getTotalPrice(List<AddTradeDto> dto) {
        return dto.stream()
                .mapToInt(AddTradeDto::getBidPrice)
                .sum();
    }
}
