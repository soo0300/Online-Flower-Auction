package com.kkoch.admin.api.controller.trade.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TradeResponse {

    private Long tradeId;
    private int totalPrice;
    private String tradeDate;
    private boolean pickupStatus;
    private int count;

    @Builder
    public TradeResponse(Long tradeId, int totalPrice, LocalDateTime tradeDate, boolean pickupStatus, int count) {
        this.tradeId = tradeId;
        this.totalPrice = totalPrice;
        this.tradeDate = tradeDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.pickupStatus = pickupStatus;
        this.count = count;
    }
}
