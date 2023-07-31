package com.kkoch.admin.domain.auction.repository.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuctionArticleSearchCond {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String code;
    private String name;
    private String type;
    private String region;

    @Builder
    private AuctionArticleSearchCond(LocalDate endDateTime, String code, String name, String type, String region) {
        this.endDateTime = endDateTime.plusDays(1).atStartOfDay();
        this.startDateTime = endDateTime.minusDays(7).atStartOfDay();
        this.code = code;
        this.name = name;
        this.type = type;
        this.region = region;
    }

    public static AuctionArticleSearchCond of(LocalDate endDateTime, String code, String type, String name, String region) {
        return new AuctionArticleSearchCond(endDateTime, code, name, type, region);
    }
}
