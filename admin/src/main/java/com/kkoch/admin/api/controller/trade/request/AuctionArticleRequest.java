package com.kkoch.admin.api.controller.trade.request;

import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AuctionArticleRequest {

    @NonNull
    private Long auctionArticleId;

    @NonNull
    private Integer bidPrice;

    @NonNull
    private LocalDateTime bidTime;

    @Builder
    private AuctionArticleRequest(Long auctionArticleId, Integer bidPrice, LocalDateTime bidTime) {
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
