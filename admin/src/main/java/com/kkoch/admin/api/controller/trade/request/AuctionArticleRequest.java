package com.kkoch.admin.api.controller.trade.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuctionArticleRequest {

    private Long auctionArticleId;
    private int bidPrice;
    private LocalDateTime bidTime;

    @Builder
    public AuctionArticleRequest(Long auctionArticleId, int bidPrice, LocalDateTime bidTime) {
        this.auctionArticleId = auctionArticleId;
        this.bidPrice = bidPrice;
        this.bidTime = bidTime;
    }
}
