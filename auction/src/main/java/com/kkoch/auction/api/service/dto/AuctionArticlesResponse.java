package com.kkoch.auction.api.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AuctionArticlesResponse implements Serializable {

    private Long auctionArticleId;
    // 상장번호
    private String auctionNumber;

    // 식물
    private String code;
    private String type;
    private String name;

    private int count;
    private int startPrice;
    private String grade;
    private String region;
    private String shipper;

    @Builder
    public AuctionArticlesResponse(Long auctionArticleId, String auctionNumber, String code, String type, String name, int count, int startPrice, String grade, String region, String shipper) {
        this.auctionArticleId = auctionArticleId;
        this.auctionNumber = auctionNumber;
        this.code = code;
        this.type = type;
        this.name = name;
        this.count = count;
        this.startPrice = startPrice;
        this.grade = grade;
        this.region = region;
        this.shipper = shipper;
    }
}