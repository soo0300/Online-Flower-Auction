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
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;
    private final AuctionArticleRepository auctionArticleRepository;

    public Long addTrade(AddTradeDto dto, LocalDateTime tradeDate) {
        Trade currnetTrade = null;

        Optional<Trade> findTrade = tradeRepository.findByMemberKey(dto.getMemberKey(), tradeDate);

        if (findTrade.isEmpty()) {
            currnetTrade = tradeRepository.save(createTradeEntity(dto, tradeDate));
        }

        if (findTrade.isPresent()) {
            currnetTrade = findTrade.get();
            currnetTrade.setTotalPrice(dto.getPrice());
        }

        AuctionArticle auctionArticle = auctionArticleRepository.findById(dto.getAuctionArticleId())
                .orElseThrow(NoSuchElementException::new);

        auctionArticle.bid(dto.getPrice(), tradeDate);
        auctionArticle.updateTrade(currnetTrade);

        return currnetTrade.getId();
    }

    private static Trade createTradeEntity(AddTradeDto dto, LocalDateTime tradeDate) {
        return Trade.builder()
                .totalPrice(dto.getPrice())
                .tradeTime(tradeDate)
                .pickupStatus(false)
                .active(true)
                .memberKey(dto.getMemberKey())
                .articles(new ArrayList<>())
                .build();
    }

    public Long pickup(Long tradeId) {
        Trade trade = getTradeEntity(tradeId);
        trade.pickup();
        return trade.getId();
    }

    public Long remove(Long tradeId) {
        Trade trade = getTradeEntity(tradeId);
        trade.remove();
        return trade.getId();
    }

    private List<AuctionArticle> getAuctionArticles(List<AddTradeDto> dto) {
        List<Long> articleIds = dto.stream()
                .map(AddTradeDto::getAuctionArticleId)
                .collect(Collectors.toList());

        return auctionArticleRepository.findByIdIn(articleIds);
    }

//    private void updateBidInfo(List<AddTradeDto> dto, List<AuctionArticle> auctionArticles) {
//        Map<Long, AuctionArticle> auctionArticleMap = auctionArticles.stream()
//                .collect(Collectors.toMap(AuctionArticle::getId, auctionArticle -> auctionArticle, (a, b) -> b));
//
//        dto.forEach(addTradeDto -> {
//            AuctionArticle auctionArticle = auctionArticleMap.get(addTradeDto.getAuctionArticleId());
//            auctionArticle.bid(addTradeDto.getBidPrice(), addTradeDto.getBidTime());
//        });
//    }
//
//    private int getTotalPrice(List<AddTradeDto> dto) {
//        return dto.stream()
//                .mapToInt(AddTradeDto::getBidPrice)
//                .sum();
//    }

//    private Trade saveTrade(Long memberId, List<AuctionArticle> auctionArticles, int totalPrice) {
//        Trade trade = Trade.createTrade(totalPrice, memberId, auctionArticles);
//        return tradeRepository.save(trade);
//    }

    private Trade getTradeEntity(Long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(NoSuchElementException::new);
    }
}
