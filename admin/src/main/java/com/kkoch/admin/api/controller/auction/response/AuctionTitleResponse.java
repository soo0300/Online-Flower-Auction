package com.kkoch.admin.api.controller.auction.response;

import lombok.Builder;
import lombok.Data;

@Data
public class AuctionTitleResponse {

    private Long auctionId;
    private String title;

    @Builder
    public AuctionTitleResponse(Long auctionId, String title) {
        this.auctionId = auctionId;
        this.title = title;
    }
}
