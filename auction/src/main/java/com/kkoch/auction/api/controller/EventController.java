package com.kkoch.auction.api.controller;

import com.kkoch.auction.api.ApiResponse;
import com.kkoch.auction.api.controller.request.EventParticipant;
import com.kkoch.auction.api.controller.response.EventResultResponse;
import com.kkoch.auction.api.service.EventService;
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

    @PostMapping("/participant")
    public ApiResponse<EventResultResponse> joinEvent(@RequestBody EventParticipant participant) {
        boolean isWinner = eventService.joinEvent(participant);
        if (isWinner) {
            // TODO: 2023-08-03 trade 전송 구현하기
            return ApiResponse.ok(EventResultResponse.success(participant));
        }
        return ApiResponse.ok(EventResultResponse.fail(participant));
    }
}
