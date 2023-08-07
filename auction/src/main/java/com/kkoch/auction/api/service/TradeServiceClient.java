package com.kkoch.auction.api.service;

import com.kkoch.auction.api.ApiResponse;
import com.kkoch.auction.api.controller.request.AddTradeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "admin-service")
public interface TradeServiceClient {

    @PostMapping("/admin-service/trades")
    ApiResponse<Long> addTrade(@RequestBody AddTradeRequest request);
}
