package com.kkoch.admin.api.controller.trade.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
public class AddTradeRequest {

    @NonNull
    private Long memberId;

    @NonNull
    List<AuctionArticleRequest> articles;

    @Builder
    private AddTradeRequest(Long memberId, List<AuctionArticleRequest> articles) {
        this.memberId = memberId;
        this.articles = articles;
    }
}
