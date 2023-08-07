package com.kkoch.admin.api.controller.trade.request;

import com.kkoch.admin.api.service.trade.dto.AddTradeDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddTradeRequest {

    @NotEmpty
    private String memberToken;

    @NotNull
    private Long auctionArticleId;

    @NotNull
    private Integer price;

    @Builder
    private AddTradeRequest(String memberToken, String auctionArticleId, Integer price) {
        this.memberToken = memberToken;
        this.auctionArticleId = Long.valueOf(auctionArticleId);
        this.price = price;
    }

    public AddTradeDto toAddTradeDto() {
        return AddTradeDto.builder()
            .memberKey(this.memberToken)
            .auctionArticleId(this.auctionArticleId)
            .price(this.price)
            .build();
    }

}
