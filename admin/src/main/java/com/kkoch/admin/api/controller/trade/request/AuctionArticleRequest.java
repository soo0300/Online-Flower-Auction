package com.kkoch.admin.api.controller.trade.request;

import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
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
    private AuctionArticleRequest(Long auctionArticleId, int bidPrice, LocalDateTime bidTime) {
        this.auctionArticleId = auctionArticleId;
        this.bidPrice = bidPrice;
        this.bidTime = bidTime;
    }

    public AddTradeDto toAddTradeDto() {
        return AddTradeDto.builder()
                .auctionArticleId(this.auctionArticleId)
                .bidPrice(this.bidPrice)
                .bidTime(this.bidTime)
                .build();
    }
}
