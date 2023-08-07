package com.kkoch.auction.api.controller.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddTradeRequest {

    private String memberToken;
    private Long auctionArticleId;
    private Integer price;

    @Builder
    private AddTradeRequest(String memberToken, String auctionArticleId, Integer price) {
        this.memberToken = memberToken;
        this.auctionArticleId = Long.valueOf(auctionArticleId);
        this.price = price;
    }
}
