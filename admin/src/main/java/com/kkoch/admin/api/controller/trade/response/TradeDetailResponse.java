package com.kkoch.admin.api.controller.trade.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class TradeDetailResponse {

    private int totalPrice;
    private String tradeTime;
    private boolean status;
    private List<AuctionArticleResponse> auctionArticles;

    @Builder
    public TradeDetailResponse(int totalPrice, LocalDateTime tradeTime, boolean status) {
        this.totalPrice = totalPrice;
        this.tradeTime = tradeTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm"));
        this.status = status;
    }

    public void insertAuctionArticles(List<AuctionArticleResponse> auctionArticles) {
        this.auctionArticles = auctionArticles;
    }
}
