package com.kkoch.admin.api.controller.trade.response;

import lombok.Data;

import java.util.List;

@Data
public class TradeDetailResponse {

    private int totalPrice;
    private String tradeTime;
    private boolean status;
    private List<AuctionArticleResponse> auctionArticles;
}
