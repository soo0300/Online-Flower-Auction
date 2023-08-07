package com.kkoch.auction.api.controller;

import com.kkoch.auction.api.ApiResponse;
import com.kkoch.auction.api.controller.request.AddTradeRequest;
import com.kkoch.auction.api.controller.request.EventParticipant;
import com.kkoch.auction.api.controller.response.EventResultResponse;
import com.kkoch.auction.api.service.EventService;
import com.kkoch.auction.api.service.TradeServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auction-service/auctions")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final TradeServiceClient tradeServiceClient;

    @PostMapping("/participant")
    public ApiResponse<EventResultResponse> joinEvent(@RequestBody EventParticipant participant) {
        boolean isWinner = eventService.joinEvent(participant);
        if (isWinner) {
            AddTradeRequest addTradeRequest = generateAddTradeRequest(participant);
            ApiResponse<Long> addTradeResponse = tradeServiceClient.addTrade(addTradeRequest);
            return ApiResponse.ok(EventResultResponse.success(participant));
        }
        return ApiResponse.ok(EventResultResponse.fail(participant));
    }

    private static AddTradeRequest generateAddTradeRequest(EventParticipant participant) {
        return AddTradeRequest.builder()
                .memberToken(participant.getMemberToken())
                .price(participant.getPrice())
                .auctionArticleId(participant.getAuctionArticleId())
                .build();
    }
}
