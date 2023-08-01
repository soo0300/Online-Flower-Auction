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
    private String type;
    private String name;
    private String region;

    @Builder
    private AuctionArticleSearchCond(LocalDate endDateTime, String code, String type, String name, String region) {
        this.endDateTime = endDateTime.plusDays(1).atStartOfDay();
        this.startDateTime = endDateTime.minusDays(6).atStartOfDay();
        this.code = code;
        this.type = type;
        this.name = name;
        this.region = region;
    }

    public static AuctionArticleSearchCond of(LocalDate endDateTime, String code, String type, String name, String region) {
        return new AuctionArticleSearchCond(endDateTime, code, type, name, region);
    }
}
