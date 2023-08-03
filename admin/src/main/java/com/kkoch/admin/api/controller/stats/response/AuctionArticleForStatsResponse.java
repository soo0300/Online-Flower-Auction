package com.kkoch.admin.api.controller.stats.response;

import com.kkoch.admin.domain.Grade;
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


}
