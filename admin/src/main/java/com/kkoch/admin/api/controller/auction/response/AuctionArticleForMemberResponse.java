package com.kkoch.admin.api.controller.auction.response;

import com.kkoch.admin.domain.Grade;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Data
@NoArgsConstructor
public class AuctionArticleForMemberResponse {

    // 식물
    private String code;
    private String name;
    private String type;

    private String grade;
    private int count;
    private int bidPrice;
    private String bidTime;
    private String region;

    @Builder
    public AuctionArticleForMemberResponse(String code, String name, String type, Grade grade, int count, int bidPrice, LocalDateTime bidTime, String region) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.grade = grade.getText();
        this.count = count;
        this.bidPrice = bidPrice;
        this.bidTime = bidTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
        this.region = region;
    }
}
