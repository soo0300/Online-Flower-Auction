package com.kkoch.auction.api.controller;

import com.kkoch.auction.api.ApiResponse;
import com.kkoch.auction.api.controller.request.AddTradeRequest;
import com.kkoch.auction.api.controller.request.EventParticipant;
import com.kkoch.auction.api.controller.response.EventResultResponse;
import com.kkoch.auction.api.service.RedisService;
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

    private final RedisService redisService;
    private final TradeServiceClient tradeServiceClient;

    @PostMapping("/participant")
    public ApiResponse<EventResultResponse> joinEvent(@RequestBody EventParticipant participant) {
        int memberNumber = redisService.getNumber(participant.getMemberKey());
        log.info("{}의 회원번호 = {}", participant.getMemberKey(), memberNumber);
        String auctionArticleId = participant.getAuctionArticleId();
        boolean isWinner = redisService.joinEvent(participant);
        if (isWinner) {
            AddTradeRequest addTradeRequest = generateAddTradeRequest(participant);
            ApiResponse<Long> addTradeResponse = tradeServiceClient.addTrade(addTradeRequest);
            String winnerPrice = redisService.getWinnerPrice(getPriceKey(auctionArticleId), participant.getMemberKey());
            return ApiResponse.ok(EventResultResponse.success(participant, String.valueOf(memberNumber), winnerPrice));
        }
        String winnerKey = redisService.getWinnerKey(auctionArticleId);
        String winnerNumber = String.valueOf(redisService.getNumber(winnerKey));
        String winnerPrice = redisService.getWinnerPrice(getPriceKey(auctionArticleId), winnerKey);
        return ApiResponse.ok(EventResultResponse.fail(participant, winnerNumber, Integer.parseInt(winnerPrice)));
    }

    private static String getPriceKey(String auctionArticleId) {
        return "price-" + auctionArticleId;
    }

    private static AddTradeRequest generateAddTradeRequest(EventParticipant participant) {
        return AddTradeRequest.builder()
                .memberKey(participant.getMemberKey())
                .price(participant.getPrice())
                .auctionArticleId(participant.getAuctionArticleId())
                .build();
    }
}
