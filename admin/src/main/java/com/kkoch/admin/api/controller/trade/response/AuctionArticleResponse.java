package com.kkoch.admin.api.controller.trade.response;

import lombok.Data;

@Data
public class AuctionArticleResponse {

    //식물 정보
    private String code;
    private String name;
    private String type;

    //경매품 정보
    private String level;
    private int count;
    private int price;
    private String bidDate;
    private String region;
}
