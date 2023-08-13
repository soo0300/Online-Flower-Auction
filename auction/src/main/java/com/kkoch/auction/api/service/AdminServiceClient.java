package com.kkoch.auction.api.service;

import com.kkoch.auction.api.ApiResponse;
import com.kkoch.auction.api.controller.request.AddTradeRequest;
import com.kkoch.auction.api.service.dto.AuctionArticlesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "admin-service")
public interface AdminServiceClient {

    @GetMapping("/admin-service/auction-articles/{auctionId}")
    ApiResponse<List<AuctionArticlesResponse>> getAuctionArticlesForAuction(@PathVariable Long auctionId);

    @PostMapping("/admin-service/trades")
    ApiResponse<Long> addTrade(@RequestBody AddTradeRequest request);
}
