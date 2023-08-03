package com.kkoch.admin.api.controller.stats.response;

import com.kkoch.admin.domain.Grade;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class AuctionArticleForStatsResponse {

    private Long plantId;

    private Grade grade;

    private int count;

    private int bidPrice;

    private LocalDateTime bidTime;

    @Builder
    public AuctionArticleForStatsResponse(Long plantId, Grade grade, int count, int bidPrice, LocalDateTime bidTime) {
        this.plantId = plantId;
        this.grade = grade;
        this.count = count;
        this.bidPrice = bidPrice;
        this.bidTime = bidTime.withNano(0);
    }
}
