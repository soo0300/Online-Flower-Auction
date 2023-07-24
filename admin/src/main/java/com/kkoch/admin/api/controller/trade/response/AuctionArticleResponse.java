package com.kkoch.admin.api.controller.trade.response;

import com.kkoch.admin.domain.Grade;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class AuctionArticleResponse {

    //식물 정보
    private String code;
    private String name;
    private String type;

    //경매품 정보
    private String grade;
    private int count;
    private int bidPrice;
    private String bidTime;
    private String region;

    @Builder
    public AuctionArticleResponse(String code, String name, String type, Grade grade, int count, int bidPrice, LocalDateTime bidTime, String region) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.grade = grade.getText();
        this.count = count;
        this.bidPrice = bidPrice;
        this.bidTime = bidTime.format(DateTimeFormatter.ofPattern("hh:mm"));
        this.region = region;
    }
}
