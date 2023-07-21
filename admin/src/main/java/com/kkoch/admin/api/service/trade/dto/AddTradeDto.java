package com.kkoch.admin.api.service.trade.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddTradeDto {

    private Long auctionArticleId;
    private int bidPrice;
    private LocalDateTime bidTime;

    @Builder
    public AddTradeDto(Long auctionArticleId, int bidPrice, LocalDateTime bidTime) {
        this.auctionArticleId = auctionArticleId;
        this.bidPrice = bidPrice;
        this.bidTime = bidTime;
    }
}
