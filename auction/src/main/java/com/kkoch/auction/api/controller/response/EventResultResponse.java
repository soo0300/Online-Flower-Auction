package com.kkoch.auction.api.controller.response;

import com.kkoch.auction.api.controller.request.EventParticipant;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventResultResponse {

    private String memberToken;
    private Long auctionArticleId;
    private int price;
    private String message;

    @Builder
    private EventResultResponse(String memberToken, String auctionArticleId, int price, String message) {
        this.memberToken = memberToken;
        this.auctionArticleId = Long.valueOf(auctionArticleId);
        this.price = price;
        this.message = message;
    }

    public static EventResultResponse fail(EventParticipant participant) {
        return EventResultResponse.builder()
                .auctionArticleId(participant.getAuctionArticleId())
                .memberToken(participant.getMemberToken())
                .price(participant.getPrice())
                .message("이미 낙찰되었습니다.")
                .build();
    }

    public static EventResultResponse success(EventParticipant participant) {
        return EventResultResponse.builder()
                .auctionArticleId(participant.getAuctionArticleId())
                .memberToken(participant.getMemberToken())
                .price(participant.getPrice())
                .message("낙찰 성공")
                .build();
    }
}
