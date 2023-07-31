package com.kkoch.admin.domain.auction.repository.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AuctionArticleSearchForAdminCond {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String code;
    private String name;
    private String type;
    private String region;
    private String shipper;

    @Builder
    private AuctionArticleSearchForAdminCond(LocalDate endDateTime, String code, String name, String type, String region, String shipper) {
        this.endDateTime = endDateTime.plusDays(1).atStartOfDay();
        this.startDateTime = endDateTime.minusDays(7).atStartOfDay();
        this.code = code;
        this.name = name;
        this.type = type;
        this.region = region;
        this.shipper = shipper;
    }

    public static AuctionArticleSearchForAdminCond of(LocalDate endDateTime, String code, String type, String name, String region, String shipper) {
        return new AuctionArticleSearchForAdminCond(endDateTime, code, name, type, region, shipper);
    }
}
