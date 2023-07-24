package com.kkoch.admin.api.service.trade;

import com.kkoch.admin.api.controller.trade.response.AuctionArticleResponse;
import com.kkoch.admin.api.controller.trade.response.TradeDetailResponse;
import com.kkoch.admin.api.controller.trade.response.TradeResponse;
import com.kkoch.admin.domain.auction.repository.AuctionArticleQueryRepository;
import com.kkoch.admin.domain.trade.repository.TradeQueryRepository;
import com.kkoch.admin.domain.trade.repository.dto.TradeSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TradeQueryService {

    private final TradeQueryRepository tradeQueryRepository;
    private final AuctionArticleQueryRepository auctionArticleQueryRepository;

    public Page<TradeResponse> getMyTrades(Long memberId, TradeSearchCond cond, Pageable pageable) {
        List<TradeResponse> responses = tradeQueryRepository.findByCondition(memberId, cond, pageable);
        int totalCount = tradeQueryRepository.getTotalCount(memberId, cond);
        return new PageImpl<>(responses, pageable, totalCount);
    }

    public TradeDetailResponse getTrade(Long tradeId) {
        TradeDetailResponse response = tradeQueryRepository.findById(tradeId);
        List<AuctionArticleResponse> contents = auctionArticleQueryRepository.findByTradeId(tradeId);
        response.insertAuctionArticles(contents);
        return response;
    }
}
