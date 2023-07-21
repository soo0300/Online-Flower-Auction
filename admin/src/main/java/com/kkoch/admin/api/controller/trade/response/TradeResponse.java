package com.kkoch.admin.api.controller.trade.response;

import lombok.Data;

@Data
public class TradeResponse {

    private int count;
    private int totalPrice;
    private String tradeTime;
    private boolean status;

}
