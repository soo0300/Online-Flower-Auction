package com.kkoch.auction.api.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EventParticipant {

    @NotNull
    private String memberToken;
    @NotNull
    private String auctionArticleId;
    @NotNull
    private Integer price;

    public EventParticipant(String memberToken, Long auctionArticleId, Integer price) {
        this.memberToken = memberToken;
        this.auctionArticleId = String.valueOf(auctionArticleId);
        this.price = price;
    }
}
