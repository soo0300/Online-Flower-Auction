package com.kkoch.admin.api.controller.trade.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddTradeRequest {

    private Long memberId;
    List<AuctionArticleRequest> articles;

    @Builder
    public AddTradeRequest(Long memberId, List<AuctionArticleRequest> articles) {
        this.memberId = memberId;
        this.articles = articles;
    }
}
