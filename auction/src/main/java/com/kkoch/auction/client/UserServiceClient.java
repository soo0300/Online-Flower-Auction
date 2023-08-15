package com.kkoch.auction.client;

import com.kkoch.auction.client.response.ReservationForAuctionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/user-service/reservations")
    ReservationForAuctionResponse getReservationForAuction(@RequestParam("plantId") Long plantId);
}
